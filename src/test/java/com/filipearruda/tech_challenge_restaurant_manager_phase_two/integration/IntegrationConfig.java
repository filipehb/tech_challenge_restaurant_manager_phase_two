package com.filipearruda.tech_challenge_restaurant_manager_phase_two.integration;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IntegrationConfig {
    public static Long createUserType(String name, MockMvc mockMvc) throws Exception {
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

    public static Long createUser(String email, String name, MockMvc mockMvc) throws Exception {
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

    public static Long createMenuItem(String name, double price, boolean availableOnlyOnSite, MockMvc mockMvc) throws Exception {
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

    public static Long createRestaurant(Long ownerId, String name, String typeKitchen, MockMvc mockMvc) throws Exception {
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

    public static String uniqueEmail(String prefix) {
        return prefix + "-" + UUID.randomUUID() + "@restaurant.com";
    }
}
