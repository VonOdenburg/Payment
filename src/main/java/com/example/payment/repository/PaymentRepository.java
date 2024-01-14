package com.example.payment.repository;

import com.example.payment.model.entity.Payment;
import com.example.payment.model.projection.MonthlyStatProjection;
import com.example.payment.model.projection.PaymentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = PaymentProjection.class)
public interface PaymentRepository extends CrudRepository<Payment, String> {

    @Query("SELECT p FROM Payment p WHERE p.currency.code = :currency")
    Page<Payment> searchByCurrency(@Param("currency") String currency, Pageable page);

    @RestResource(exported = false)
    @Query(value = "SELECT AVG(sum) FROM payment WHERE currency_code=:currency_code", nativeQuery = true)
    Optional<BigDecimal> getAverage(@Param("currency_code") String currency_code);

    @RestResource(exported = false)
    @Query(value = "SELECT AVG(sum) as \"value\", COUNT(*) as \"count\", MONTH(paid) as \"month\" FROM payment " +
            "WHERE currency_code=:currency_code AND YEAR(paid)=:year " +
            "GROUP BY MONTH(paid) ORDER BY MONTH(paid)", nativeQuery = true)
    List<MonthlyStatProjection> getMonthlyStat(@Param("currency_code") String currency_code, @Param("year") Integer year);
}
