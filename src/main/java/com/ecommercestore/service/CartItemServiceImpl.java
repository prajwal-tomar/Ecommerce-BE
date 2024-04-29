package com.ecommercestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercestore.models.Cart;
import com.ecommercestore.models.CartItem;
import com.ecommercestore.models.Product;
import com.ecommercestore.models.User;
import com.ecommercestore.repository.CartItemRepository;
import com.ecommercestore.repository.UserRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity()); // Product Price x Quantity
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws Exception {
        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("CartItem not found"));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        System.out.println(existingUser);

        // Check if the existing cart item belongs to the user
        if (!existingCartItem.getUserId().equals(userId)) {
            throw new Exception("User does not have permission to update this cart item");
        }

        // Update cart item properties
        existingCartItem.setQuantity(cartItem.getQuantity());
        existingCartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        existingCartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        return cartItemRepository.save(existingCartItem);
    }

    @Override
    public CartItem isCartItemExists(Cart cart, Product product, String size, Long userId) {
        return cartItemRepository.isCartItemExists(cart, product, size, userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("Cart item not found"));

        if (existingCartItem.getUserId().equals(userId)) {
            cartItemRepository.delete(existingCartItem);
        } else {
            throw new Exception("Could not delete: User does not have permission");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws Exception {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("CartItem not found."));
    }

}
