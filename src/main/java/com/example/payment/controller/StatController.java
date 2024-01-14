package com.example.payment.controller;

import com.example.payment.model.dto.Average;
import com.example.payment.model.dto.Stat;
import com.example.payment.service.StatService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class StatController {
    private StatService statService;

    @GetMapping(path = "/stat/average")
    public Average getAverage(@RequestParam(name = "currency") @NotBlank String currency_code) {
        return statService.getAverage(currency_code);
    }

    @GetMapping(path = "/stat/monthly-stat")
    public Map<Integer, Stat> getMonthlyStat(
            @RequestParam(name = "currency") @NotBlank String currency_code,
            @RequestParam @NotNull Integer year) {


        return statService.getMonthlyStat(currency_code, year);
    }
}
