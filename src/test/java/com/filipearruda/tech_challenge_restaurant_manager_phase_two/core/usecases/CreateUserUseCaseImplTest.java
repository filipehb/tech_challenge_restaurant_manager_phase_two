package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    private CreateUserUseCaseImpl createUserUseCase;

    @BeforeEach
    void setUp() {
        createUserUseCase = new CreateUserUseCaseImpl(userGateway);
    }

    @Test
    void shouldCreateUser() {
        User user = mock(User.class);
        when(userGateway.create(user)).thenReturn(1L);

        Long result = createUserUseCase.create(user);

        assertEquals(1L, result);
        verify(userGateway).create(user);
    }
}
