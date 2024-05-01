package com.ecommercestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercestore.models.Cart;
import com.ecommercestore.models.User;
import com.ecommercestore.request.AddItemRequest;
import com.ecommercestore.service.CartService;
import com.ecommercestore.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public ResponseEntity<Cart> getCartDetails(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJWT(jwt);
        System.out.println(user);
        Cart cart = cartService.findByUserId(user.getId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestHeader("Authorization") String jwt,
            @RequestBody AddItemRequest req) throws Exception {
        User user = userService.findUserByJWT(jwt);
        String response = cartService.addCartItem(user.getId(), req);

        return ResponseEntity.ok(response);
    }

}
