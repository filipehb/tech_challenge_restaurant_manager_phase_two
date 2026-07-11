package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.UserNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.UserTypeNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociateUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    private AssociateUserUseCaseImpl associateUserUseCase;

    @BeforeEach
    void setUp() {
        associateUserUseCase = new AssociateUserUseCaseImpl(userGateway, userTypeGateway);
    }

    @Test
    void shouldAssociateUserToUserType() {
        Long userId = 1L;
        Long userTypeId = 2L;
        User user = new User(userId, "test@test.com", "password", "Test User", null);
        UserType userType = new UserType(userTypeId, "Admin");

        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.of(userType));
        when(userGateway.update(user)).thenReturn(userId);

        Long result = associateUserUseCase.associateUserToUserType(userId, userTypeId);

        assertEquals(userId, result);
        assertEquals(userType, user.getUserType());
        verify(userGateway).findById(userId);
        verify(userTypeGateway).findById(userTypeId);
        verify(userGateway).update(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;
        Long userTypeId = 2L;

        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            associateUserUseCase.associateUserToUserType(userId, userTypeId);
        });

        verify(userGateway).findById(userId);
        verifyNoInteractions(userTypeGateway);
        verify(userGateway, never()).update(any());
    }

    @Test
    void shouldThrowExceptionWhenUserTypeNotFound() {
        Long userId = 1L;
        Long userTypeId = 2L;
        User user = new User(userId, "test@test.com", "password", "Test User", null);

        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> {
            associateUserUseCase.associateUserToUserType(userId, userTypeId);
        });

        verify(userGateway).findById(userId);
        verify(userTypeGateway).findById(userTypeId);
        verify(userGateway, never()).update(any());
    }
}
