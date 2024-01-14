package com.example.payment.integartion;

import jakarta.json.JsonObject;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class StatIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void testGetAverageWhenNoCurrencyShouldResponsesWith400() throws Exception {
        mockMvc
                .perform(get("/stat/average"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Required parameter 'currency' is not present.")));
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/AverageZero.json")
    public void testGetAverageWhenCurrencyIsPresentAndNoRecordShouldReturn0(JsonObject json) throws Exception {
        MvcResult result = mockMvc
                .perform(get("/stat/average").param("currency", "HUF"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/AverageTen.json")
    public void testGetAverageWhenCurrencyIsPresentAndNoRecordShouldReturnAverageOfPayments(JsonObject json) throws Exception {
        createDBResourcesWith2Payments();

        MvcResult result = mockMvc
                .perform(get("/stat/average").param("currency", "HUF"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @Test
    public void testGetMonthlyStatWhenNoCurrencyShouldResponsesWith400() throws Exception {
        mockMvc
                .perform(get("/stat/monthly-stat"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Required parameter 'currency' is not present.")));
    }

    @Test
    public void testGetMonthlyStatWhenNoYearShouldResponsesWith400() throws Exception {
        mockMvc
                .perform(get("/stat/monthly-stat").param("currency", "HUF"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Required parameter 'year' is not present.")));
    }

    @Test
    public void testGetMonthlyStatWhenYearIsNotIntShouldResponsesWith400() throws Exception {
        mockMvc
                .perform(get("/stat/monthly-stat")
                        .param("currency", "HUF")
                        .param("year", "HUF"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/StatZero.json")
    public void testGetMonthlyStatWhenParamsAreGoodAndNoResultsFoundShouldResponsesWithZeroStats(JsonObject json) throws Exception {
        MvcResult result = mockMvc
                .perform(get("/stat/monthly-stat")
                        .param("currency", "HUF")
                        .param("year", "2024"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/Stat.json")
    public void testGetMonthlyStatWhenParamsAreGoodAndResultsFoundShouldResponsesWithZeroStats(JsonObject json) throws Exception {
        createDBResourcesWith2Payments();
        MvcResult result = mockMvc
                .perform(get("/stat/monthly-stat")
                        .param("currency", "HUF")
                        .param("year", "2022"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }
}
