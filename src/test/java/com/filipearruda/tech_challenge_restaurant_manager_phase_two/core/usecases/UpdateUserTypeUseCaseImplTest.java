package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private UpdateUserTypeUseCaseImpl updateUserTypeUseCase;

    @BeforeEach
    void setUp() {
        updateUserTypeUseCase = new UpdateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Test
    void shouldUpdateUserType() {
        Long id = 1L;
        UserType userType = mock(UserType.class);
        when(userTypeGateway.update(id, userType)).thenReturn(userType);

        UserType result = updateUserTypeUseCase.update(id, userType);

        assertEquals(userType, result);
        verify(userTypeGateway).update(id, userType);
    }
}
