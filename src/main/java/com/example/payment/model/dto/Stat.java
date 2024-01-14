package com.example.payment.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Stat {
    private BigDecimal average;
    private int count;
}
