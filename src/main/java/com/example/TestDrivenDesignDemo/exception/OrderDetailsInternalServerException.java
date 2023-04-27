package com.example.TestDrivenDesignDemo.exception;

public class OrderDetailsInternalServerException extends RuntimeException{
    public String exceptionMessage;

    public OrderDetailsInternalServerException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
