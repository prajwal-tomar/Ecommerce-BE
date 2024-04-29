package com.ecommercestore.models;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class PaymentInformation {

    private String cardHolderName;

    private String cardNumber;

    private LocalDateTime expirationDate;

    private String cvv;

}
