package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.config;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlerTest {

    private CustomExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CustomExceptionHandler();
    }

    @Test
    void shouldHandleCreateExceptionsAsBadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST.value(),
                handler.handleCreateMenuItemsException(new CreateMenuItemsException("menu")).getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(),
                handler.handleCreateRestaurantException(new CreateRestaurantException("restaurant")).getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(),
                handler.handleCreateUserException(new CreateUserException("user")).getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(),
                handler.handleCreateUserTypeException(new CreateUserTypeException("userType")).getStatus());
    }

    @Test
    void shouldHandleDomainAndValidationExceptionsAsBadRequest() {
        ProblemDetail domain = handler.handleDomainException(new DomainException("domain"));
        ProblemDetail validation = handler.handleValidationException(new ValidationException("validation"));
        ProblemDetail menuItem = handler.handleMenuItemNotFoundException(new MenuItemNotFoundException("menu"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), domain.getStatus());
        assertEquals("validation", validation.getDetail());
        assertEquals(HttpStatus.BAD_REQUEST.value(), menuItem.getStatus());
    }

    @Test
    void shouldHandleNotFoundExceptions() {
        assertEquals(HttpStatus.NOT_FOUND.value(),
                handler.handleRestaurantNotFoundException(new RestaurantNotFoundException("restaurant")).getStatus());
        assertEquals(HttpStatus.NOT_FOUND.value(),
                handler.handleUserNotFoundException(new UserNotFoundException("user")).getStatus());
        assertEquals(HttpStatus.NOT_FOUND.value(),
                handler.handleUserTypeNotFoundException(new UserTypeNotFoundException("userType")).getStatus());
    }

    @Test
    void shouldHandleGenericExceptionAsInternalServerError() {
        ProblemDetail detail = handler.HandleGenericError(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), detail.getStatus());
        assertEquals("boom", detail.getDetail());
    }
}
