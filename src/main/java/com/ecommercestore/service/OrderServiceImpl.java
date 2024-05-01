package com.ecommercestore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercestore.models.Address;
import com.ecommercestore.models.Cart;
import com.ecommercestore.models.CartItem;
import com.ecommercestore.models.Order;
import com.ecommercestore.models.OrderItem;
import com.ecommercestore.models.PaymentDetails;
import com.ecommercestore.models.User;
import com.ecommercestore.repository.AddressRepository;
import com.ecommercestore.repository.CartRepository;
import com.ecommercestore.repository.OrderRepository;
import com.ecommercestore.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        try {
            // Associate user with address
            shippingAddress.setUser(user);
            addressRepository.save(shippingAddress);

            // associate address with user as the relationship is bidirectional
            user.getAddresses().add(shippingAddress);
            userRepository.save(user);

            // Let's build the cart
            Cart cart = cartItemService.findByUserId(user.getId());

            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString()); // Generate a unique order ID
            order.setUser(user);
            order.setOrderDate(LocalDateTime.now()); // Set the order date to the current time
            order.setDeliveryDate(LocalDateTime.now().plusDays(7)); // Assuming a standard delivery time of 7 days
            order.setShippingAddress(shippingAddress);
            order.getPaymentDetails().setStatus("PENDING");
            order.setPrice(cart.getTotalPrice()); // Set the order price to the total price of the cart
            order.setDiscountedPrice(cart.getTotalDiscountedPrice());
            order.setDiscount(cart.getDiscount());
            order.setOrderStatus("PENDING"); // Assuming a default order status of "PENDING"
            order.setNumberOfItems(cart.getTotalItems());
            order.setCreatedAt(LocalDateTime.now());

            // Assuming you have a method to convert CartItems to OrderItems
            List<OrderItem> orderItems = convertCartItemsToOrderItems(cart.getCartItems(), order);

            System.out.println(order);
            order.setOrderItems(orderItems);

            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create order", e);
        }
    }

    public List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order order) {
        return cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order); // Set the order reference
                    orderItem.setProduct(cartItem.getProduct()); // Copy product reference
                    orderItem.setSize(cartItem.getSize()); // Copy size
                    orderItem.setQuantity(cartItem.getQuantity()); // Copy quantity
                    orderItem.setPrice(cartItem.getPrice().doubleValue()); // Convert price to double
                    orderItem.setDiscountedPrice(cartItem.getDiscountedPrice()); // Copy discounted price
                    orderItem.setUserId(cartItem.getUserId()); // Copy user ID
                    orderItem.setDeliveryDate(LocalDateTime.now().plusDays(7));
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).get();
    }

    @Override
    // Find all orders made by an user
    public List<Order> userOrderHistory(Long userId) throws Exception {
        return orderRepository.getUserOrders(userId);
    }

    @Override
    public Order placeOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);

        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");

        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);

        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shipOrder(Long orderId) throws Exception {
        // gets the order
        Order order = findOrderById(orderId);

        // now change the paymentstatus and order status of the order
        order.setOrderStatus("SHIPPED");

        return orderRepository.save(order);
    }

    @Override
    public Order deliverOrder(Long orderId) throws Exception {
        // gets the order
        Order order = findOrderById(orderId);

        // now change the paymentstatus and order status of the order
        order.setOrderStatus("DELIVERED");

        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId) throws Exception {
        // gets the order
        Order order = findOrderById(orderId);

        // now change the paymentstatus and order status of the order
        order.setOrderStatus("CANCELLED");

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() throws Exception {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws Exception {
        orderRepository.deleteById(orderId);
        System.out.println("Order deleted successfully.");
    }

}
