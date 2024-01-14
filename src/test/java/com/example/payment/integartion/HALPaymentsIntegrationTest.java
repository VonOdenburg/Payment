package com.example.payment.integartion;


import com.example.payment.model.entity.Category;
import com.example.payment.model.entity.Currency;
import com.example.payment.model.entity.Payment;
import jakarta.json.JsonObject;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HALPaymentsIntegrationTest extends AbstractIntegrationTest {

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/payment.json")
    public void testCreate(JsonObject json) throws Exception {
        Currency currency = createHUF();
        Category category = createTravel();

        mockMvc
                .perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());

        Payment payment = paymentRepository.findAll().iterator().next();

        assertEquals(payment.getCategory(), category);
        assertEquals(payment.getCurrency(), currency);
        assertEquals(payment.getSummary(), json.getString("summary"));
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALPaymentsEmpty.json")
    public void testFindAllShouldReturnEmptyListWhenDBIsEmpty(JsonObject json) throws Exception {
        MvcResult result = mockMvc
                .perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALPayments.json")
    public void testFindAllShouldReturnListOFPayments(JsonObject json) throws Exception {
        createDBResourcesWith2Payments();

        MvcResult result = mockMvc
                .perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALPaymentsSearchHUF.json")
    public void testSearchByCurrencyWhenHasPaymentsWithCurrencyShouldReturnListOFPayments(JsonObject json) throws Exception {
        createDBResourcesWith2Payments();

        MvcResult result = mockMvc
                .perform(get("/api/payments/search/searchByCurrency")
                        .param("currency", "HUF"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALPaymentsSearchEmpty.json")
    public void testSearchByCurrencyWhenDosNotHavePaymentsWithCurrencyShouldReturnListOFPayments(JsonObject json) throws Exception {
        createDBResourcesWith2Payments();

        MvcResult result = mockMvc
                .perform(get("/api/payments/search/searchByCurrency")
                        .param("currency", "USD"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }
}
