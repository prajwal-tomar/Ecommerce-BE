package com.ecommercestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercestore.models.Order;
import com.ecommercestore.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler() throws Exception {
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<Order> confirmOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.confirmOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ship/{orderId}")
    public ResponseEntity<Order> shipOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.shipOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deliver/{orderId}")
    public ResponseEntity<Order> deliverOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.deliverOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.cancelOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
