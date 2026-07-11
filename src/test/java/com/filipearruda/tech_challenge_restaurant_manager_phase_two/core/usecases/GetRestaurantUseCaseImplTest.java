package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.RestaurantNotFoundException;
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
class GetRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    private GetRestaurantUseCaseImpl getRestaurantUseCase;

    @BeforeEach
    void setUp() {
        getRestaurantUseCase = new GetRestaurantUseCaseImpl(restaurantGateway);
    }

    @Test
    void shouldGetRestaurant() {
        Long id = 1L;
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantGateway.findById(id)).thenReturn(Optional.of(restaurant));

        Restaurant result = getRestaurantUseCase.get(id);

        assertEquals(restaurant, result);
        verify(restaurantGateway).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        Long id = 1L;
        when(restaurantGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> getRestaurantUseCase.get(id));

        verify(restaurantGateway).findById(id);
    }
}
