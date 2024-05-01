package com.ecommercestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercestore.models.User;
import com.ecommercestore.service.CartItemService;
import com.ecommercestore.service.UserService;

@RestController
@RequestMapping("/api/cart_item")

public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@RequestHeader("Authorization") String jwt,
            @PathVariable("cartItemId") Long cartItemId)
            throws Exception {
        User user = userService.findUserByJWT(jwt);
        Long userId = user.getId();
        cartItemService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok("Cart item deleted successfully!");
    }

}
