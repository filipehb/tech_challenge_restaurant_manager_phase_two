package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    private UpdateRestaurantUseCaseImpl updateRestaurantUseCase;

    @BeforeEach
    void setUp() {
        updateRestaurantUseCase = new UpdateRestaurantUseCaseImpl(restaurantGateway);
    }

    @Test
    void shouldUpdateRestaurant() {
        Long id = 1L;
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantGateway.update(id, restaurant)).thenReturn(restaurant);

        Restaurant result = updateRestaurantUseCase.update(id, restaurant);

        assertEquals(restaurant, result);
        verify(restaurantGateway).update(id, restaurant);
    }
}
