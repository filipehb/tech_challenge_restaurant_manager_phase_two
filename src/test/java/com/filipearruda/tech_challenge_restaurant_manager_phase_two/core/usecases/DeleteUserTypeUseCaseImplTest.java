package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private DeleteUserTypeUseCaseImpl deleteUserTypeUseCase;

    @BeforeEach
    void setUp() {
        deleteUserTypeUseCase = new DeleteUserTypeUseCaseImpl(userTypeGateway);
    }

    @Test
    void shouldDeleteUserType() {
        Long id = 1L;

        deleteUserTypeUseCase.delete(id);

        verify(userTypeGateway).delete(id);
    }
}
