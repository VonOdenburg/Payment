package com.example.payment.service;

import com.example.payment.model.dto.Average;
import com.example.payment.model.dto.Stat;
import com.example.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private StatService statService;

    @Test
    void getAverageWhenReceiveOptionalWithoutValueShouldReturnAverageWith0Value() {
        when(paymentRepository.getAverage(anyString())).thenReturn(Optional.empty());

        Average actual = statService.getAverage("HUF");

        assertEquals(new Average(BigDecimal.ZERO), actual);
    }

    @Test
    void getAverageWhenReceiveOptionalWitValueShouldReturnAverageWithSameValue() {
        when(paymentRepository.getAverage(anyString())).thenReturn(Optional.of(BigDecimal.TEN));

        Average actual = statService.getAverage("HUF");

        assertEquals(new Average(BigDecimal.TEN), actual);
    }

    @Test
    void getAverageWhenCalledShouldCallRepoWithSameParam() {
        when(paymentRepository.getAverage(anyString())).thenReturn(Optional.of(BigDecimal.TEN));

        statService.getAverage("HUF");

        verify(paymentRepository, times(1)).getAverage(eq("HUF"));
    }

    @Test
    void getMonthlyStatWhenCalledShouldCallRepoWithSameParams() {
        when(paymentRepository.getMonthlyStat(anyString(), anyInt())).thenReturn(List.of());

        statService.getMonthlyStat("HUF", 2022);

        verify(paymentRepository, times(1)).getMonthlyStat(eq("HUF"), eq(2022));
    }

    @Test
    void getMonthlyStatWhenEmptyListReceivedShouldReturnWIthMapOf12MonthsSat() {
        when(paymentRepository.getMonthlyStat(anyString(), anyInt())).thenReturn(List.of());

        Map<Integer, Stat> actual = statService.getMonthlyStat("HUF", 2022);

        Map<Integer, Stat> expected =
                IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toMap(i -> i, i ->
                        Stat.builder().average(BigDecimal.ZERO).count(0).build(), (a, b) -> b));

        assertEquals(expected, actual);
    }
}