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
class CreateRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    private CreateRestaurantUseCaseImpl createRestaurantUseCase;

    @BeforeEach
    void setUp() {
        createRestaurantUseCase = new CreateRestaurantUseCaseImpl(restaurantGateway);
    }

    @Test
    void shouldCreateRestaurant() {
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantGateway.create(restaurant)).thenReturn(1L);

        Long result = createRestaurantUseCase.create(restaurant);

        assertEquals(1L, result);
        verify(restaurantGateway).create(restaurant);
    }
}
