package de.service.order.application.model;

import de.service.order.application.model.enums.OrderStateEnum;
import org.springframework.http.HttpStatus;

public class OrderResponse {
    private String id;
    private OrderStateEnum orderStateEnum;
    private String message;
    private HttpStatus status;

    private OrderResponse(String id, OrderStateEnum orderStateEnum, String message, HttpStatus status) {
        this.id = id;
        this.orderStateEnum = orderStateEnum;
        this.message = message;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public OrderStateEnum getOrderState() {
        return orderStateEnum;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static class OrderBuilder {
        private String id;
        private OrderStateEnum orderStateEnum;
        private String message;
        private HttpStatus status;

        public OrderBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withOrderState(OrderStateEnum orderStateEnum) {
            this.orderStateEnum = orderStateEnum;
            return this;
        }

        public OrderBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public OrderBuilder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public OrderResponse build() {
            return new OrderResponse(id, orderStateEnum, message, status);
        }
    }
}
