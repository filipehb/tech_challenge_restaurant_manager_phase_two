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
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static String uniqueEmail(String prefix) {
        return prefix + "-" + UUID.randomUUID() + "@restaurant.com";
    }

    @Test
    void shouldCreateGetAndUpdateUserType() throws Exception {
        Long userTypeId = createUserType("OWNER");

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

    @Test
    void shouldCreateUserAndAssociateToUserType() throws Exception {
        Long userTypeId = createUserType("CUSTOMER");
        String email = uniqueEmail("customer");
        Long userId = createUser(email, "Customer User");

        mockMvc.perform(post("/api/v1/user/associate-user/{userId}/to-type/{userTypeId}", userId, userTypeId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.name").value("Customer User"))
                .andExpect(jsonPath("$.userType.name").value("CUSTOMER"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void shouldCreateGetUpdateAndDeleteMenuItem() throws Exception {
        Long menuItemId = createMenuItem("Feijoada", 45.9, false);

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

    @Test
    void shouldCreateGetUpdateAndDeleteRestaurant() throws Exception {
        Long ownerId = createUser(uniqueEmail("owner"), "Restaurant Owner");
        Long restaurantId = createRestaurant(ownerId, "Cantina do Filipe", "FAST_CASUAL");

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
        Long ownerId = createUser(uniqueEmail("owner"), "Owner");
        Long restaurantId = createRestaurant(ownerId, "Cantina Associacao", "FAST_CASUAL");
        Long menuItemId = createMenuItem("Moqueca", 55.0, false);

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

    private Long createUserType(String name) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/user-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s"
                                }
                                """.formatted(name)))
                .andExpect(status().isCreated())
                .andReturn();

        return Long.valueOf(result.getResponse().getContentAsString());
    }

    private Long createUser(String email, String name) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "secret123",
                                  "name": "%s"
                                }
                                """.formatted(email, name)))
                .andExpect(status().isCreated())
                .andReturn();

        return Long.valueOf(result.getResponse().getContentAsString());
    }

    private Long createMenuItem(String name, double price, boolean availableOnlyOnSite) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "price": %s,
                                  "description": "Test item",
                                  "image": "/img/test.jpg",
                                  "availableOnlyOnSite": %s
                                }
                                """.formatted(name, price, availableOnlyOnSite)))
                .andExpect(status().isCreated())
                .andReturn();

        return Long.valueOf(result.getResponse().getContentAsString());
    }

    private Long createRestaurant(Long ownerId, String name, String typeKitchen) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "address": "Rua das Flores, 100",
                                  "typeKitchen": "%s",
                                  "restaurantSchedule": {
                                    "weeklyHours": {
                                      "MONDAY": [
                                        { "startHour": "11:00:00", "endHour": "15:00:00" }
                                      ],
                                      "FRIDAY": [
                                        { "startHour": "18:00:00", "endHour": "23:00:00" }
                                      ]
                                    }
                                  },
                                  "owner": %d,
                                  "menuItems": []
                                }
                                """.formatted(name, typeKitchen, ownerId)))
                .andExpect(status().isCreated())
                .andReturn();

        return Long.valueOf(result.getResponse().getContentAsString());
    }
}
