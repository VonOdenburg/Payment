package com.example.payment.repository;

import com.example.payment.model.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, String> {
}
