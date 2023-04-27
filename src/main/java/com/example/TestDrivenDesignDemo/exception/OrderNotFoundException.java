package com.example.TestDrivenDesignDemo.exception;

public class OrderNotFoundException extends RuntimeException{
    public String exceptionMessage;
    public OrderNotFoundException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
