package com.example.TestDrivenDesignDemo.tdd;

import com.example.TestDrivenDesignDemo.entity.OrderResponse;
import com.example.TestDrivenDesignDemo.exception.OrderDetailsInternalServerException;
import com.example.TestDrivenDesignDemo.exception.OrderNotFoundException;
import com.example.TestDrivenDesignDemo.service.OrderDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailsServiceTest {
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @InjectMocks
    private OrderDetailsServiceImpl orderDetailsService;



    @Test
    void getOrderDetailsTest() {
//
//        StepVerifier.create(orderDetailsService.getOrderDetails())
//                .expectNextMatches(response -> response instanceof OrderResponse)
//                .expectComplete()
//                .verify();

when(reactiveMongoTemplate.findAll(any()))
                .thenReturn(Flux.just(OrderResponse.builder()
                        .orderId("123")
                        .productName("Apple Watch")
                        .productCompany("Apple")
                        .productPrice(BigInteger.valueOf(30000))
                        .build()));
        StepVerifier.create(orderDetailsService.getOrderDetails())
                .expectNextMatches(response -> response instanceof OrderResponse
                        && "123".equals(response.getOrderId())
                        && "Apple Watch".equals(response.getProductName())
                        && "Apple".equals(response.getProductCompany())
                        && BigInteger.valueOf(30000).equals(response.getProductPrice()) )
                .expectComplete()
                .verify();

    }

    @Test
    void getOrderDetails_InternalServerExceptionTest() {
        doThrow(
                new OrderDetailsInternalServerException("Internal server exception while retrieving order details"))
                .when(reactiveMongoTemplate)
                .findAll(any());
        Assertions.assertThrowsExactly(OrderDetailsInternalServerException.class,
                () -> {
                    orderDetailsService.getOrderDetails().subscribe();
                }, "Internal server exception while retrieving order details");
    }


 @Test
    void getOrderDetails_DataNotFoundExceptionTest() {
        doThrow(
                new OrderNotFoundException("Order Details Not Found"))
                .when(reactiveMongoTemplate)
                .findAll(any());
        Assertions.assertThrowsExactly(OrderNotFoundException.class,
                () -> {
                    orderDetailsService.getOrderDetails().subscribe();
                }, "Order Details Not Found");
    }

}
