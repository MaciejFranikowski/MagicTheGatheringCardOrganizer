package com.maciejfranikowski.magicTheGatheringCardOrganizer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class ScryFallApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void searchAutoCompleteCardNames() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cards/autocomplete/{name}", "Force"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));
    }
}
