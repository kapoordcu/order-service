package de.service.order.application.exception;

public class OrderException extends RuntimeException {
    private String error;

    public OrderException(String error) {
        this.error = error;
    }
}
