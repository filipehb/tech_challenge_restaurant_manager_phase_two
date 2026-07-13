package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantTest {

    @Test
    void shouldAddMenuItemWhenNotAlreadyAssociated() {
        Restaurant restaurant = new Restaurant(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                new RestaurantSchedule(),
                10L
        );
        MenuItem menuItem = new MenuItem(1L, "Feijoada", 45.90, "Completa", "/img.jpg", false);

        restaurant.addMenuItem(menuItem);

        assertEquals(1, restaurant.getMenuItems().size());
        assertEquals(menuItem, restaurant.getMenuItems().getFirst());
    }

    @Test
    void shouldNotAddDuplicateMenuItemById() {
        Restaurant restaurant = new Restaurant(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                new RestaurantSchedule(),
                10L
        );
        MenuItem first = new MenuItem(1L, "Feijoada", 45.90, "Completa", "/img.jpg", false);
        MenuItem duplicate = new MenuItem(1L, "Feijoada 2", 50.0, "Outra", "/img2.jpg", true);

        restaurant.addMenuItem(first);
        restaurant.addMenuItem(duplicate);

        assertEquals(1, restaurant.getMenuItems().size());
        assertEquals(first, restaurant.getMenuItems().getFirst());
    }

    @Test
    void shouldAddMenuItemWithoutIdEvenIfAnotherWithoutIdExists() {
        Restaurant restaurant = new Restaurant(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                new RestaurantSchedule(),
                10L
        );
        MenuItem first = new MenuItem("Feijoada", 45.90, "Completa", "/img.jpg", false);
        MenuItem second = new MenuItem("Moqueca", 55.0, "Peixe", "/img2.jpg", true);

        restaurant.addMenuItem(first);
        restaurant.addMenuItem(second);

        assertEquals(2, restaurant.getMenuItems().size());
    }
}
