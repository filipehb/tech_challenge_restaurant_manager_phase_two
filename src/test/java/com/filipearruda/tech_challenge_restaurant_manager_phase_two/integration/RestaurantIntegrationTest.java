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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class RestaurantIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateGetUpdateAndDeleteRestaurant() throws Exception {
        Long ownerId = IntegrationConfig.createUser(IntegrationConfig.uniqueEmail("owner"), "Restaurant Owner", mockMvc);
        Long restaurantId = IntegrationConfig.createRestaurant(ownerId, "Cantina do Filipe", "FAST_CASUAL", mockMvc);

        mockMvc.perform(get("/api/v1/restaurants/{id}", restaurantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cantina do Filipe"))
                .andExpect(jsonPath("$.address").value("Rua das Flores, 100"))
                .andExpect(jsonPath("$.typeKitchen").value("FAST_CASUAL"))
                .andExpect(jsonPath("$.owner").value(ownerId))
                .andExpect(jsonPath("$.restaurantSchedule.weeklyHours.MONDAY", hasSize(1)));

        mockMvc.perform(put("/api/v1/restaurants/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Cantina Atualizada",
                                  "address": "Av. Paulista, 1000",
                                  "typeKitchen": "COMMERCIAL",
                                  "restaurantSchedule": {
                                    "weeklyHours": {
                                      "SATURDAY": [
                                        { "startHour": "12:00:00", "endHour": "22:00:00" }
                                      ]
                                    }
                                  },
                                  "owner": %d,
                                  "menuItems": []
                                }
                                """.formatted(ownerId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cantina Atualizada"))
                .andExpect(jsonPath("$.typeKitchen").value("COMMERCIAL"))
                .andExpect(jsonPath("$.restaurantSchedule.weeklyHours.SATURDAY", hasSize(1)));

        mockMvc.perform(delete("/api/v1/restaurants/{id}", restaurantId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/restaurants/{id}", restaurantId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAssociateMenuItemToRestaurant() throws Exception {
        Long ownerId = IntegrationConfig.createUser(IntegrationConfig.uniqueEmail("owner"), "Owner", mockMvc);
        Long restaurantId = IntegrationConfig.createRestaurant(ownerId, "Cantina Associacao", "FAST_CASUAL", mockMvc);
        Long menuItemId = IntegrationConfig.createMenuItem("Moqueca", 55.0, false, mockMvc);

        mockMvc.perform(post(
                        "/api/v1/restaurants/associate-menu-item/{menuItemId}/to-restaurant/{restaurantId}",
                        menuItemId,
                        restaurantId
                ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.menuItems", hasSize(1)))
                .andExpect(jsonPath("$.menuItems[0].name").value("Moqueca"));
    }

    @Test
    void shouldReturnNotFoundForMissingRestaurant() throws Exception {
        mockMvc.perform(get("/api/v1/restaurants/{id}", 999999L))
                .andExpect(status().isNotFound());
    }
}
