package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.MenuItemNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.MenuItemGateway;
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
class GetMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    private GetMenuItemUseCaseImpl getMenuItemUseCase;

    @BeforeEach
    void setUp() {
        getMenuItemUseCase = new GetMenuItemUseCaseImpl(menuItemGateway);
    }

    @Test
    void shouldGetMenuItem() {
        Long id = 1L;
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItemGateway.findById(id)).thenReturn(Optional.of(menuItem));

        MenuItem result = getMenuItemUseCase.get(id);

        assertEquals(menuItem, result);
        verify(menuItemGateway).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenMenuItemNotFound() {
        Long id = 1L;
        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> getMenuItemUseCase.get(id));
        verify(menuItemGateway).findById(id);
    }
}
