package com.ecommercestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercestore.exceptions.UserException;
import com.ecommercestore.models.Address;
import com.ecommercestore.models.Order;
import com.ecommercestore.models.User;
import com.ecommercestore.service.OrderService;
import com.ecommercestore.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String jwt,
            @RequestBody Address shippingAddress) throws UserException {
        User user = userService.findUserByJWT(jwt);
        Order createdOrder = orderService.createOrder(user, shippingAddress);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long id) throws Exception {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> userOrderHistory(@PathVariable Long userId) throws Exception {
        List<Order> orders = orderService.userOrderHistory(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/place/{id}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long id) throws Exception {
        Order placedOrder = orderService.placeOrder(id);
        return ResponseEntity.ok(placedOrder);
    }

}
