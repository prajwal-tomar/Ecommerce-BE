package com.ecommercestore.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String orderId;

    @ManyToOne 
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address shippingAddress;

    @Embedded
    private PaymentDetails PaymentDetails = new PaymentDetails();

    private double price;

    private Integer discountedPrice;

    private Integer discount;

    private String orderStatus;

    private Integer numberOfItems;

    private LocalDateTime createdAt;

}
