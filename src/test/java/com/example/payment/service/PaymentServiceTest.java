package com.example.payment.service;

import com.example.payment.model.entity.Category;
import com.example.payment.model.entity.Currency;
import com.example.payment.model.entity.Payment;
import com.example.payment.model.projection.PaymentProjection;
import com.example.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void testGetPaymentsSGouldTransformData() {
        ZonedDateTime datetime = ZonedDateTime.parse("2022-04-22T11:16:00Z");
        Category category = new Category("travel", "Travel");
        Currency currency = new Currency("HUF", "Ft", "Forint");
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setCategory(category);
        payment.setCurrency(currency);
        payment.setPaid(datetime);
        payment.setSummary("Test");
        payment.setSum(BigDecimal.ONE);

        when(paymentRepository.findAll()).thenReturn(List.of(payment));

        List<PaymentProjection> payments = paymentService.getPayments();

        verify(paymentRepository, times(1)).findAll();
        assertEquals(1, payments.size());
        PaymentProjection projection = payments.getFirst();
        assertEquals(1L, projection.getId());
        assertEquals("HUF", projection.getCurrency());
        assertEquals("travel", projection.getCategory());
        assertEquals("Test", projection.getSummary());
        assertEquals(BigDecimal.ONE, projection.getSum());
        assertEquals(datetime, projection.getPaid());
    }

}