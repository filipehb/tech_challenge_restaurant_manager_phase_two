package com.filipearruda.tech_challenge_restaurant_manager_phase_two.integration;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateUserAndAssociateToUserType() throws Exception {
        Long userTypeId = IntegrationConfig.createUserType("CUSTOMER", mockMvc);
        String email = IntegrationConfig.uniqueEmail("customer");
        Long userId = IntegrationConfig.createUser(email, "Customer User", mockMvc);

        mockMvc.perform(post("/api/v1/user/associate-user/{userId}/to-type/{userTypeId}", userId, userTypeId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.name").value("Customer User"))
                .andExpect(jsonPath("$.userType.name").value("CUSTOMER"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }
}
