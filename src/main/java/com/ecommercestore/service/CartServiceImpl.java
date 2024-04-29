package com.ecommercestore.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercestore.models.Cart;
import com.ecommercestore.models.CartItem;
import com.ecommercestore.models.Product;
import com.ecommercestore.models.User;
import com.ecommercestore.repository.CartRepository;
import com.ecommercestore.repository.UserRepository;
import com.ecommercestore.request.AddItemRequest;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setActive(true);
        return cartRepository.save(cart);
    }

    @Override // userId will be taken from jwt.
    public String addCartItem(Long userId, AddItemRequest req) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        // find the cart associated with the user
        Cart cart = cartRepository.findByUser(user);

        // product associated with AddItemRequest
        Product product = productService.getProductById(req.getProductId());

        // cartitem exists or not?
        CartItem cartItem = cartItemService.isCartItemExists(cart, product, req.getSize(), userId);

        if (cartItem == null) {
            CartItem cartItem2 = new CartItem();
            cartItem2.setProduct(product);
            cartItem2.setCart(cart);
            cartItem2.setUserId(userId);
            cartItem2.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem2);

            // Add to cart
            cart.getCartItems().add(createdCartItem);
            return "New item added to the cart!";
        } else {
            // Increase the quantity by 1
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            return "Existing item quantity increased!";
        }
    }

    @Override
    public Cart findByUserId(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Cart cart = cartRepository.findByUser(user);

        Integer totalPrice = 0;
        Integer totalDiscountedPrice = 0;
        Integer totalItems = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItems += cartItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItems(totalItems);
        cart.setDiscount(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }

}
