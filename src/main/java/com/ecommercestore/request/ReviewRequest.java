package com.ecommercestore.request;

import lombok.Data;

@Data
public class ReviewRequest {

    private String review;
    // Product with which it is associated.
    private Long productId;

}
