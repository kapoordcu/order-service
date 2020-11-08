package de.service.order.application.service;

import de.service.order.application.model.enums.PaymentStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    public PaymentStateEnum processPayment(String orderId, double amount) {
        LOGGER.info("Payment for order with id: {} and amount {} is initiated", orderId, amount);
        return PaymentStateEnum.SUCCESS;
    }
}
