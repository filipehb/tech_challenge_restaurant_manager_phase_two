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
class CreateUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private CreateUserTypeUseCaseImpl createUserTypeUseCase;

    @BeforeEach
    void setUp() {
        createUserTypeUseCase = new CreateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Test
    void shouldCreateUserType() {
        UserType userType = mock(UserType.class);
        when(userTypeGateway.create(userType)).thenReturn(1L);

        Long result = createUserTypeUseCase.create(userType);

        assertEquals(1L, result);
        verify(userTypeGateway).create(userType);
    }
}
