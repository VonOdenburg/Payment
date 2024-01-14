package com.example.payment.integartion;


import com.example.payment.model.entity.Currency;
import jakarta.json.JsonObject;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HALCurrencyIntegrationTest extends AbstractIntegrationTest {
    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HUF.json")
    public void testCreate(JsonObject json) throws Exception {
        mockMvc
                .perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());

        Optional<Currency> huf = currencyRepository.findById("HUF");
        assertEquals(new Currency(
                        json.getString("code"),
                        json.getString("sortName"),
                        json.getString("name")), huf.get());
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALCurrenciesEmpty.json")
    public void testFindAllShouldReturnEmptyListWhenDBIsEmpty(JsonObject json) throws Exception {
        MvcResult result = mockMvc
                .perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALCurrenciesHUF.json")
    public void testFindAllShouldReturnListOFCurrencies(JsonObject json) throws Exception {
        createHUF();

        MvcResult result = mockMvc
                .perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }
}
