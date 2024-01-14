package com.example.payment.service;

import com.example.payment.model.dto.Average;
import com.example.payment.model.dto.Stat;
import com.example.payment.model.projection.MonthlyStatProjection;
import com.example.payment.repository.PaymentRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class StatService {
    private PaymentRepository paymentRepository;

    public Average getAverage(@NotBlank String currency_code) {
        Optional<BigDecimal> average = paymentRepository.getAverage(currency_code);
        return new Average(average.orElse(BigDecimal.ZERO));
    }

    public Map<Integer, Stat> getMonthlyStat(@NotBlank String currency_code, @NotEmpty Integer year) {
        List<MonthlyStatProjection> statProjections = paymentRepository.getMonthlyStat(currency_code, year);
        return IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toMap(i -> i, i -> createStat(statProjections, i), (a, b) -> b));
    }

    private Stat createStat(List<MonthlyStatProjection> statProjections, int month) {
        Optional<MonthlyStatProjection> statProjection = statProjections.stream().filter(p -> p.getMonth() == month).findAny();

        return Stat
                .builder()
                .count(statProjection.map(MonthlyStatProjection::getCount).orElse(0))
                .average(statProjection.isPresent() ? statProjection.get().getValue() : BigDecimal.ZERO)
                .build();
    }
}
