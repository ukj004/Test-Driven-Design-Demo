package com.example.TestDrivenDesignDemo.controller;

import com.example.TestDrivenDesignDemo.entity.OrderResponse;
import com.example.TestDrivenDesignDemo.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

@RestController
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("/getOrderDetails")
    @ResponseStatus(HttpStatus.OK)
    public Flux<OrderResponse> getOrderDetails() {
//            return Flux.just(OrderResponse.builder()
//                    .orderId("123")
//                    .productName("Apple Watch")
//                    .productCompany("Apple")
//                    .productPrice(BigInteger.valueOf(30000))
//                    .build());
        return orderDetailsService.getOrderDetails();
        }
}
