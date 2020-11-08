package de.service.order.application.model;

public class PaymentRequest {
    private String id;
    private double amount;

    public PaymentRequest(String id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
}
