package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.MenuItemGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    private CreateMenuItemUseCaseImpl createMenuItemUseCase;

    @BeforeEach
    void setUp() {
        createMenuItemUseCase = new CreateMenuItemUseCaseImpl(menuItemGateway);
    }

    @Test
    void shouldCreateMenuItem() {
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItemGateway.create(menuItem)).thenReturn(1L);

        Long result = createMenuItemUseCase.create(menuItem);

        assertEquals(1L, result);
        verify(menuItemGateway).create(menuItem);
    }
}
