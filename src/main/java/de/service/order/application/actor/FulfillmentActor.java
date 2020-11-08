package de.service.order.application.actor;

import akka.actor.AbstractActor;
import de.service.order.application.model.Order;
import de.service.order.application.service.MessageService;
import de.service.order.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

@Actor
public class FulfillmentActor extends AbstractActor {

    @Autowired
    private OrderService orderService;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Order.OrderBuilder.class, orderService::passToAkkaActor)
                .build();
    }
}
