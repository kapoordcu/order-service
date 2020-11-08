package de.service.order.application.service;

import de.service.order.application.comps.PaymentLogic;
import de.service.order.application.model.Item;
import de.service.order.application.model.Order;
import de.service.order.application.model.enums.OrderStateEnum;
import de.service.order.application.model.enums.PaymentStateEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Value("${order.minAmount:10}")
    private int minAmount;

    @Value("${order.minPricePerItem:0}")
    private Double minPricePerItem;

    @Value("${order.maxPricePerItem:1000}")
    private Double maxPricePerItem;

    private PaymentLogic paymentLogic;

    public OrderService(PaymentLogic paymentLogic) {
        this.paymentLogic = paymentLogic;
    }

    /**
     * Order creation process should ensure sane requirements for an order to be accepted.
     *
     * @param itemList
     * @return
     */
    public boolean orderValidated(List<Item> itemList) {
        List<Item> collect = itemList.stream()
                .filter(item -> item.getAmount() < minAmount)
                .filter(item -> item.getUnitPrice() >= minPricePerItem && item.getUnitPrice() <= maxPricePerItem)
                .collect(Collectors.toList());
        return collect.size() == itemList.size();
    }

    public void passToAkkaActor(Order.OrderBuilder orderBuilder) {

    }

    public Order.OrderBuilder initiatePayment(String orderId, List<Item> itemList, OrderStateEnum orderStatus) {
        Order.OrderBuilder orderCreated = new Order.OrderBuilder()
                .withOrderId(orderId)
                .withState(orderStatus);
        PaymentStateEnum paymentStatus = paymentLogic.processPayment(orderId, itemList);
        if(PaymentStateEnum.SUCCESS.equals(paymentStatus)) {
            new Order.OrderBuilder()
                    .withOrderId(orderCreated.getOrderId())
                    .withState(OrderStateEnum.PAID);
        }
        return orderCreated;
    }
}
