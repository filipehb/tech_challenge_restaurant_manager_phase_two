package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.TypeKitchen;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantScheduleDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;
    @Mock
    private GetRestaurantUseCase getRestaurantUseCase;
    @Mock
    private DeleteRestaurantUseCase deleteRestaurantUseCase;
    @Mock
    private UpdateRestaurantUseCase updateRestaurantUseCase;
    @Mock
    private AssociateMenuItemUseCase associateMenuItemUseCase;

    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        restaurantController = new RestaurantController(
                restaurantMapper,
                createRestaurantUseCase,
                getRestaurantUseCase,
                deleteRestaurantUseCase,
                updateRestaurantUseCase,
                associateMenuItemUseCase
        );
    }

    @Test
    void shouldCreateRestaurant() {
        RestaurantInputDto input = new RestaurantInputDto(
                "Cantina", "Rua 1", TypeKitchen.FAST_CASUAL, new RestaurantScheduleDto(Map.of()), 10L, List.of()
        );
        Restaurant domain = mock(Restaurant.class);
        when(restaurantMapper.mapToDomain(input)).thenReturn(domain);
        when(createRestaurantUseCase.create(domain)).thenReturn(1L);

        Long result = restaurantController.create(input);

        assertEquals(1L, result);
        verify(createRestaurantUseCase).create(domain);
    }

    @Test
    void shouldGetRestaurant() {
        Restaurant domain = mock(Restaurant.class);
        RestaurantOutputDto output = mock(RestaurantOutputDto.class);
        when(getRestaurantUseCase.get(1L)).thenReturn(domain);
        when(restaurantMapper.mapToOutputDto(domain)).thenReturn(output);

        RestaurantOutputDto result = restaurantController.get(1L);

        assertEquals(output, result);
    }

    @Test
    void shouldDeleteRestaurant() {
        restaurantController.delete(1L);
        verify(deleteRestaurantUseCase).delete(1L);
    }

    @Test
    void shouldUpdateRestaurant() {
        RestaurantInputDto input = new RestaurantInputDto(
                "Cantina", "Rua 1", TypeKitchen.FAST_CASUAL, new RestaurantScheduleDto(Map.of()), 10L, List.of()
        );
        Restaurant domain = mock(Restaurant.class);
        Restaurant updated = mock(Restaurant.class);
        RestaurantOutputDto output = mock(RestaurantOutputDto.class);
        when(restaurantMapper.mapToDomain(input)).thenReturn(domain);
        when(updateRestaurantUseCase.update(1L, domain)).thenReturn(updated);
        when(restaurantMapper.mapToOutputDto(updated)).thenReturn(output);

        RestaurantOutputDto result = restaurantController.update(1L, input);

        assertEquals(output, result);
    }

    @Test
    void shouldAssociateMenuItem() {
        Restaurant domain = mock(Restaurant.class);
        RestaurantOutputDto output = mock(RestaurantOutputDto.class);
        when(associateMenuItemUseCase.associateMenuItemToRestaurant(1L, 2L)).thenReturn(domain);
        when(restaurantMapper.mapToOutputDto(domain)).thenReturn(output);

        RestaurantOutputDto result = restaurantController.associateMenuItemToRestaurant(1L, 2L);

        assertEquals(output, result);
    }
}
