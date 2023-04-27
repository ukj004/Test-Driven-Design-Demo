package com.example.TestDrivenDesignDemo.service;

import com.example.TestDrivenDesignDemo.entity.OrderResponse;
import com.example.TestDrivenDesignDemo.exception.OrderDetailsInternalServerException;
import com.example.TestDrivenDesignDemo.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<OrderResponse> getOrderDetails() {
        //return Flux.just(new OrderResponse());

        return reactiveMongoTemplate.findAll(OrderResponse.class)
                .onErrorResume(
                        ex ->Mono.error(
                                        new OrderDetailsInternalServerException("Internal server exception while retrieving order details")))
                .switchIfEmpty(
                        Mono.error(
                                new OrderNotFoundException("Order Details Not Found")));
    }
}
