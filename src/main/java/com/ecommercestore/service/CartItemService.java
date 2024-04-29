package com.ecommercestore.service;

import com.ecommercestore.models.Cart;
import com.ecommercestore.models.CartItem;
import com.ecommercestore.models.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws Exception;

    public CartItem isCartItemExists(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws Exception;

    public CartItem findCartItemById(Long cartItemId) throws Exception;
}
