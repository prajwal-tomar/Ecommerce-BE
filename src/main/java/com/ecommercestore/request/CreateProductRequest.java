package com.ecommercestore.request;

import java.util.HashSet;
import java.util.Set;

import com.ecommercestore.models.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String title;
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer discountPercentage;
    private Integer quantity;
    private String brand;
    private String color;

    private Set<Size> sizes = new HashSet<>();
    private String imageUrl;
    private Integer numRatings;

    private String topLevelCategory; // men
    private String secondLevelCategory; // men/clothing
    private String thirdLevelCategory; // men/clothing/shirt
}
