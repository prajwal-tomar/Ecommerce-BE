package com.ecommercestore.service;

import com.ecommercestore.models.Cart;
import com.ecommercestore.models.User;
import com.ecommercestore.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws Exception;

    public Cart findByUserId(Long userId) throws Exception;

}
