package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
