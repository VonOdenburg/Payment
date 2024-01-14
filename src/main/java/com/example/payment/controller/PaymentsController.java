package com.example.payment.controller;

import com.example.payment.model.projection.PaymentProjection;
import com.example.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PaymentsController {
    private PaymentService paymentService;

    @GetMapping(path = "/payments")
    public List<PaymentProjection> getPayments() {
        return paymentService.getPayments();
    }
}
