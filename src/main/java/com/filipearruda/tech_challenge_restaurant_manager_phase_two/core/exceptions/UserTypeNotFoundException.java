package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions;

public class UserTypeNotFoundException extends RuntimeException {
    public UserTypeNotFoundException(String message) {
        super(message);
    }
}
