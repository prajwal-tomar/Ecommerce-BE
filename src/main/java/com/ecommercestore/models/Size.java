package com.ecommercestore.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Size {
    private String name;
    private Integer quantity;
}
