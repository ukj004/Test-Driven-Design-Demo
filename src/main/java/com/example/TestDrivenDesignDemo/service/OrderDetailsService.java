package com.example.TestDrivenDesignDemo.service;

import com.example.TestDrivenDesignDemo.entity.OrderResponse;
import reactor.core.publisher.Flux;

public interface OrderDetailsService {

    Flux<OrderResponse> getOrderDetails();
}
