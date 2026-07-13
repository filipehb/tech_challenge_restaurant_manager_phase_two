package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.UserTypeNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private GetUserTypeUseCaseImpl getUserTypeUseCase;

    @BeforeEach
    void setUp() {
        getUserTypeUseCase = new GetUserTypeUseCaseImpl(userTypeGateway);
    }

    @Test
    void shouldGetUserType() {
        Long id = 1L;
        UserType userType = mock(UserType.class);
        when(userTypeGateway.findById(id)).thenReturn(Optional.of(userType));

        UserType result = getUserTypeUseCase.get(id);

        assertEquals(userType, result);
        verify(userTypeGateway).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeNotFound() {
        Long id = 1L;
        when(userTypeGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> getUserTypeUseCase.get(id));
        verify(userTypeGateway).findById(id);
    }
}
