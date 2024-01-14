package com.example.payment.integartion;

import com.example.payment.model.entity.Category;
import com.example.payment.model.entity.Currency;
import com.example.payment.model.entity.Payment;
import com.example.payment.repository.CategoryRepository;
import com.example.payment.repository.CurrencyRepository;
import com.example.payment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class AbstractIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CategoryRepository categoryRepository;
    ObjectMapper mapper = new ObjectMapper();

    void createDBResourcesWith2Payments() {
        Currency currency = createHUF();
        Category category = createTravel();
        createPayments(category, currency);
    }

    void createPayments(Category category, Currency currency) {
        createPayment(category, currency, ZonedDateTime.parse("2022-04-21T11:16:00Z"), "Test 1", BigDecimal.valueOf(15));
        createPayment(category, currency, ZonedDateTime.parse("2022-04-22T11:16:00Z"), "Test 2", BigDecimal.valueOf(5));
    }

    Category createTravel() {
        Category category = new Category("travel", "Travel");
        categoryRepository.save(category);
        return category;
    }

    Currency createHUF() {
        Currency currency = new Currency("HUF", "Ft", "Forint");
        currencyRepository.save(currency);
        return currency;
    }

    void createPayment(Category category, Currency currency, ZonedDateTime paid, String summary, BigDecimal sum) {
        Payment payment = new Payment();
        payment.setCategory(category);
        payment.setCurrency(currency);
        payment.setPaid(paid);
        payment.setSummary(summary);
        payment.setSum(sum);
        paymentRepository.save(payment);
    }

    void compareResultWithJson(Object json, MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        String content = result.getResponse().getContentAsString();

        assertEquals(mapper.readTree(json.toString()), mapper.readTree(content));
    }

    @AfterEach
    public void tearDown() {
        paymentRepository.deleteAll();
        categoryRepository.deleteAll();
        currencyRepository.deleteAll();
    }
}
