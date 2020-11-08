package de.service.order.application.model;

public class Item {
    private String itemId;
    private String category;
    private int amount;
    private double unitPrice;

    public String getItemId() {
        return itemId;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
