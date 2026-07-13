package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.RestaurantSchedule;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.TypeKitchen;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.MenuItemNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.RestaurantNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.MenuItemGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.RestaurantGateway;
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
class AssociateMenuItemUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private MenuItemGateway menuItemGateway;

    private AssociateMenuItemUseCaseImpl associateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        associateMenuItemUseCase = new AssociateMenuItemUseCaseImpl(restaurantGateway, menuItemGateway);
    }

    @Test
    void shouldAssociateMenuItemToRestaurant() {
        Long restaurantId = 1L;
        Long menuItemId = 2L;
        Restaurant restaurant = new Restaurant(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                new RestaurantSchedule(),
                10L
        );
        MenuItem menuItem = new MenuItem(menuItemId, "Feijoada", 45.90, "Completa", "/img.jpg", false);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(menuItem));
        when(restaurantGateway.update(restaurantId, restaurant)).thenReturn(restaurant);

        Restaurant result = associateMenuItemUseCase.associateMenuItemToRestaurant(restaurantId, menuItemId);

        assertEquals(1, result.getMenuItems().size());
        assertEquals(menuItem, result.getMenuItems().getFirst());
        verify(restaurantGateway).findById(restaurantId);
        verify(menuItemGateway).findById(menuItemId);
        verify(restaurantGateway).update(restaurantId, restaurant);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        Long restaurantId = 1L;
        Long menuItemId = 2L;

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () ->
                associateMenuItemUseCase.associateMenuItemToRestaurant(restaurantId, menuItemId)
        );

        verify(restaurantGateway).findById(restaurantId);
        verifyNoInteractions(menuItemGateway);
        verify(restaurantGateway, never()).update(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenMenuItemNotFound() {
        Long restaurantId = 1L;
        Long menuItemId = 2L;
        Restaurant restaurant = new Restaurant(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                new RestaurantSchedule(),
                10L
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () ->
                associateMenuItemUseCase.associateMenuItemToRestaurant(restaurantId, menuItemId)
        );

        verify(restaurantGateway).findById(restaurantId);
        verify(menuItemGateway).findById(menuItemId);
        verify(restaurantGateway, never()).update(any(), any());
    }
}
