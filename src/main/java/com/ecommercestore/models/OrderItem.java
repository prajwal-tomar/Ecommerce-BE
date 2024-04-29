package com.ecommercestore.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne
    @JsonIgnore
    private Order order;

    @ManyToOne // Many order items can have the same product in them.
    private Product product;

    private String size;

    private Integer quantity;

    private double price;

    private Integer discountedPrice;

    private Integer discount;

    private Long userId;

    private LocalDateTime deliveryDate;

}
