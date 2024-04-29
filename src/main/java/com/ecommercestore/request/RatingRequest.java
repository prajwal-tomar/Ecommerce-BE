package com.ecommercestore.request;

import lombok.Data;

@Data
public class RatingRequest {

    private Long productId;
    private Double rating;

}
