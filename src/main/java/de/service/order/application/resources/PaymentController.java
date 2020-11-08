package de.service.order.application.resources;

import de.service.order.application.model.PaymentRequest;
import de.service.order.application.model.enums.PaymentStateEnum;
import de.service.order.application.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/api/v1")
public class PaymentController {
    private PaymentService paymentService;
    private Map<String, PaymentRequest> paymentData = new HashMap<>();

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * a callback to a REST endpoint which confirms the order as paid
     *
     * @param paymentRequest
     * @return
     */
    @PostMapping(value = "/payments/init")
    public ResponseEntity<PaymentStateEnum> initiatePayment(@RequestHeader(name = "idempotentOrder", required = true) String idempotentOrder, @RequestBody PaymentRequest paymentRequest) {
        if(paymentData.containsKey(idempotentOrder)) {
            return ResponseEntity.badRequest().body(PaymentStateEnum.DUPLICATE_REQUEST);
        }
        PaymentStateEnum paymentStatus = paymentService.processPayment(paymentRequest.getId(), paymentRequest.getAmount());
        if(PaymentStateEnum.SUCCESS.equals(paymentStatus)) {
            paymentData.put(idempotentOrder, paymentRequest);
            return ResponseEntity.ok(PaymentStateEnum.SUCCESS);
        }
        return ResponseEntity.badRequest().body(PaymentStateEnum.FAILED);
    }
}
