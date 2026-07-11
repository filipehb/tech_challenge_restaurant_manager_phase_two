package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
