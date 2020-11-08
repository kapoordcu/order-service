package de.service.order.application.model;

import de.service.order.application.model.enums.OrderStateEnum;

public class Order {
    private String orderId;
    private OrderStateEnum state;

    private Order(String orderId, OrderStateEnum state) {
        this.orderId = orderId;
        this.state = state;
    }


    public static class OrderBuilder {
        private String orderId;
        private OrderStateEnum state;

        public OrderBuilder withOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderBuilder withState(OrderStateEnum state) {
            this.state = state;
            return this;
        }

        public String getOrderId() {
            return orderId;
        }

        public OrderStateEnum getState() {
            return state;
        }
    }
}
