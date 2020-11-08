package de.service.order.application.comps;

import de.service.order.application.model.*;
import de.service.order.application.model.enums.PaymentStateEnum;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class PaymentLogic {
    private static final RestTemplate restTemplate = new RestTemplate();

    public PaymentStateEnum processPayment(String orderId, List<Item> itemList) {
        Double totalAmount = itemList.stream()
                .map(i -> (i.getUnitPrice() * i.getAmount()))
                .reduce(0.0, (a, b) -> a + b);
        return invokePaymentService(orderId, totalAmount);
    }

    private PaymentStateEnum invokePaymentService(String id, Double totalAmount) {
        PaymentRequest paymentRequest = new PaymentRequest(id, totalAmount);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("idempotentOrder", id);
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);
        return restTemplate.postForObject("/api/v1/payments/init", entity, PaymentStateEnum.class);
    }
}
