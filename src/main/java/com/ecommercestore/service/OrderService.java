package com.ecommercestore.service;

import java.util.List;

import com.ecommercestore.models.Address;
import com.ecommercestore.models.Order;
import com.ecommercestore.models.User;

public interface OrderService {
    public Order createOrder(Order order, User user, Address shippingAddress);

    public Order findOrderById(Long id) throws Exception;

    public List<Order> userOrderHistory(Long userId) throws Exception;

    public Order placeOrder(Long orderId) throws Exception;

    public Order confirmOrder(Long orderId) throws Exception;

    public Order shipOrder(Long orderId) throws Exception;

    public Order deliverOrder(Long orderId) throws Exception;

    public Order cancelOrder(Long orderId) throws Exception;

    public List<Order> getAllOrders() throws Exception;

    public void deleteOrder(Long orderId) throws Exception;
}
