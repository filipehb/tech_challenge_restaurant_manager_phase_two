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
class UpdateMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    private UpdateMenuItemUseCaseImpl updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        updateMenuItemUseCase = new UpdateMenuItemUseCaseImpl(menuItemGateway);
    }

    @Test
    void shouldUpdateMenuItem() {
        Long id = 1L;
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItemGateway.update(id, menuItem)).thenReturn(menuItem);

        MenuItem result = updateMenuItemUseCase.update(id, menuItem);

        assertEquals(menuItem, result);
        verify(menuItemGateway).update(id, menuItem);
    }
}
