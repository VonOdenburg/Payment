package com.example.payment.model.projection;

import com.example.payment.model.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Projection(name = "paymentProjection", types = { Payment.class })
public interface PaymentProjection {
    Long getId();
    @Value("#{target.getCategory().getId()}")
    String getCategory();
    @Value("#{target.getCurrency().getCode()}")
    String getCurrency();
    String getSummary();
    BigDecimal getSum();
    ZonedDateTime getPaid();
}
