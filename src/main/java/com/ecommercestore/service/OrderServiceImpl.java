package com.ecommercestore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommercestore.models.Address;
import com.ecommercestore.models.Order;
import com.ecommercestore.models.User;

@Service
public class OrderServiceImpl implements OrderService {

    // @Autowired
    // private CartRepository CartRepository;

    // @Autowired
    // private CartService CartItemService;

    // @Autowired
    // private ProductService productService;

    @Override
    public Order createOrder(Order order, User user, Address shippingAddress) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public Order findOrderById(Long id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOrderById'");
    }

    @Override
    public List<Order> userOrderHistory(Long userId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userOrderHistory'");
    }

    @Override
    public Order placeOrder(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeOrder'");
    }

    @Override
    public Order confirmOrder(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmOrder'");
    }

    @Override
    public Order shipOrder(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shipOrder'");
    }

    @Override
    public Order deliverOrder(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deliverOrder'");
    }

    @Override
    public Order cancelOrder(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrder'");
    }

    @Override
    public List<Order> getAllOrders() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }

    @Override
    public void deleteOrder(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }

}
