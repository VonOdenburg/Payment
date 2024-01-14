package com.example.payment.service;


import com.example.payment.model.entity.Payment;
import com.example.payment.model.projection.PaymentProjection;
import com.example.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class PaymentService {
    private PaymentRepository paymentRepository;

    public List<PaymentProjection> getPayments() {
        return StreamSupport
                .stream(paymentRepository.findAll().spliterator(), false)
                .map(PaymentService::transform).collect(Collectors.toList());
    }

    private static PaymentProjection transform(Payment p) {
        return new PaymentProjection() {
            @Override
            public Long getId() {
                return p.getId();
            }

            @Override
            public String getCategory() {
                return p.getCategory().getId();
            }

            @Override
            public String getCurrency() {
                return p.getCurrency().getCode();
            }

            @Override
            public String getSummary() {
                return p.getSummary();
            }

            @Override
            public BigDecimal getSum() {
                return p.getSum();
            }

            @Override
            public ZonedDateTime getPaid() {
                return p.getPaid();
            }
        };
    }
}
