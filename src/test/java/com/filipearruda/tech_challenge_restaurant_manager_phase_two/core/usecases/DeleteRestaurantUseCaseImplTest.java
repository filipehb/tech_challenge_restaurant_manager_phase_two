package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    private DeleteRestaurantUseCaseImpl deleteRestaurantUseCase;

    @BeforeEach
    void setUp() {
        deleteRestaurantUseCase = new DeleteRestaurantUseCaseImpl(restaurantGateway);
    }

    @Test
    void shouldDeleteRestaurant() {
        Long id = 1L;

        deleteRestaurantUseCase.delete(id);

        verify(restaurantGateway).delete(id);
    }
}
