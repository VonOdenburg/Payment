package com.example.payment.integartion;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PaymentsIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testGetPayments() throws Exception {
        createDBResourcesWith2Payments();
        mockMvc
                .perform(get("/payments"))
                .andExpect(status().isOk());
    }
}
