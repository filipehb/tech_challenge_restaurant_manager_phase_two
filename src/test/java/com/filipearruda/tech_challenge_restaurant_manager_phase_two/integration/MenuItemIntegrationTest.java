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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class MenuItemIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateGetUpdateAndDeleteMenuItem() throws Exception {
        Long menuItemId = IntegrationConfig.createMenuItem("Feijoada", 45.9, false, mockMvc);

        mockMvc.perform(get("/api/v1/menu/{id}", menuItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Feijoada"))
                .andExpect(jsonPath("$.price").value(45.9));

        mockMvc.perform(put("/api/v1/menu/{id}", menuItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Feijoada Especial",
                                  "price": 49.9,
                                  "description": "Completa com torresmo",
                                  "image": "/img/feijoada.jpg",
                                  "availableOnlyOnSite": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Feijoada Especial"))
                .andExpect(jsonPath("$.price").value(49.9))
                .andExpect(jsonPath("$.availableOnlyOnSite").value(true));

        mockMvc.perform(delete("/api/v1/menu/{id}", menuItemId))
                .andExpect(status().isNoContent());
    }
}
