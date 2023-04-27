package com.example.TestDrivenDesignDemo.tdd;

import com.example.TestDrivenDesignDemo.controller.OrderDetailsController;
import com.example.TestDrivenDesignDemo.entity.OrderResponse;
import com.example.TestDrivenDesignDemo.exception.OrderNotFoundException;
import com.example.TestDrivenDesignDemo.service.OrderDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailsControllerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OrderDetailsController orderDetailsController;

    @MockBean
    private OrderDetailsService orderDetailsService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    /**
     * This is to test a service is up or not. By this time we have not defined the service
     * so, this test will fail as we expect 200(ok) but will get 404(Not Defined).
     * Now, we will refactor the code to pass this test.
     */
    @Test
    void checkOrderDetails_serviceIsUp() {
        webTestClient
                .get()
                .uri("/getOrderDetails")
                .exchange()
                .expectStatus()
                .isOk();
    }

    /**
     * This is to test the controller class as it is giving the desired response or not.
     * so, this test will fail as we have not defined the service method.
     * Now, we will refactor the code to pass this test by adding service method returning normal object.
     */
    @Test
    void checkOrderDetails_Controller_CorrectResponse() {
        when(orderDetailsService.getOrderDetails())
                .thenReturn(Flux.just(OrderResponse.builder()
                        .orderId("123")
                        .productName("Apple Watch")
                        .productCompany("Apple")
                        .productPrice(BigInteger.valueOf(30000))
                        .build()));
        StepVerifier.create(orderDetailsController.getOrderDetails())
                .expectNextMatches(response -> response instanceof OrderResponse
                        && "123".equals(response.getOrderId())
                        && "Apple Watch".equals(response.getProductName())
                        && "Apple".equals(response.getProductCompany())
                        && BigInteger.valueOf(30000).equals(response.getProductPrice()))
                .expectComplete()
                .verify();
    }
    /**
     * This is to test the controller class as it is giving the desired response or not.
     * so, this test will pass as we have mocked service method to return Exception.
     */
    /*@Test
    void checkOrderDetails_Controller_ForDataNotFoundException() {
        doReturn(Flux.error(new OrderNotFoundException("Order Details Not Found"))).when(orderDetailsService).getOrderDetails();
        StepVerifier.create(orderDetailsController.getOrderDetails())
                .expectNextCount(0)
                .expectErrorMatches(throwable -> throwable instanceof OrderNotFoundException
                        && ((OrderNotFoundException) throwable).exceptionMessage.equals("Order Details Not Found")
                )
                .verify();
    }*/
}
