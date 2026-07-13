package com.filipearruda.tech_challenge_restaurant_manager_phase_two.integration;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class UserTypeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateGetAndUpdateUserType() throws Exception {
        Long userTypeId = IntegrationConfig.createUserType("OWNER", mockMvc);

        mockMvc.perform(get("/api/v1/user-type/{id}", userTypeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("OWNER"));

        mockMvc.perform(put("/api/v1/user-type/{id}", userTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "RESTAURANT_OWNER"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("RESTAURANT_OWNER"));
    }
}
