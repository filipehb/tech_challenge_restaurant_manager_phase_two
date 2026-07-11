package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
