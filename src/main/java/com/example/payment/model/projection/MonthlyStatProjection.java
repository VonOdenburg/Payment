package com.example.payment.model.projection;

import java.math.BigDecimal;

public interface MonthlyStatProjection {
    BigDecimal getValue();
    int getCount();
    int getMonth();
}
