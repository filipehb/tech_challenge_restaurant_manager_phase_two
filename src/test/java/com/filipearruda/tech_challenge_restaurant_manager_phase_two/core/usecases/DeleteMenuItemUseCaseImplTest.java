package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.MenuItemGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    private DeleteMenuItemUseCaseImpl deleteMenuItemUseCase;

    @BeforeEach
    void setUp() {
        deleteMenuItemUseCase = new DeleteMenuItemUseCaseImpl(menuItemGateway);
    }

    @Test
    void shouldDeleteMenuItem() {
        Long id = 1L;

        deleteMenuItemUseCase.delete(id);

        verify(menuItemGateway).delete(id);
    }
}
