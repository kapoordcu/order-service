package de.service.order.application.resources;

import de.service.order.application.exception.OrderException;
import de.service.order.application.model.*;
import de.service.order.application.model.enums.OrderStateEnum;
import de.service.order.application.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController("/api/v1")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * For an order to be created, it is triggered by a REST endpoint
     * which accepts a basket of items of your choice.
     *
     * @param itemList
     */
    @PostMapping(value = "/order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody List<Item> itemList) {
        Optional.of(orderService.orderValidated(itemList))
                .orElseThrow(() -> new OrderException("ORDER_REJECTED, Requirements for orders are not sane"));

        String orderId = UUID.randomUUID().toString();
        Order.OrderBuilder orderBuilder = orderService.initiatePayment(orderId, itemList, OrderStateEnum.CREATED);
        if(OrderStateEnum.PAID.equals(orderBuilder.getState())) {
            orderService.passToAkkaActor(orderBuilder);
        }
        return null;
    }

}
