package com.example.payment.integartion;


import com.example.payment.model.entity.Category;
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

public class HALCategoryIntegrationTest extends AbstractIntegrationTest {
    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/housing.json")
    public void testCreate(JsonObject housing) throws Exception {
        mockMvc
                .perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(housing.toString()))
                .andExpect(status().isCreated());

        Optional<Category> expected = categoryRepository.findById("housing");
        assertEquals(new Category(
                housing.getString("id"),
                housing.getString("name")), expected.get());
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALCategoriesEmpty.json")
    public void testFindAllShouldReturnEmptyListWhenDBIsEmpty(JsonObject json) throws Exception {
        MvcResult result = mockMvc
                .perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/jsons/HALCategoriesTravel.json")
    public void testFindAllShouldReturnListOFCurrencies(JsonObject json) throws Exception {
        createTravel();

        MvcResult result = mockMvc
                .perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andReturn();

        compareResultWithJson(json, result);
    }
}
