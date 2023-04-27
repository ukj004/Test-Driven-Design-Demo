package com.example.TestDrivenDesignDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponse {
    private String orderId;
    private String productName;
    private BigInteger productPrice;
    private String productCompany;
}
