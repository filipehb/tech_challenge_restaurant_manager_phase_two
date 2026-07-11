package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantOutputDto;

public class RestaurantMapper {
    private final RestaurantScheduleMapper restaurantScheduleMapper;
    private final MenuItemMapper menuItemMapper;

    public RestaurantMapper(RestaurantScheduleMapper restaurantScheduleMapper, MenuItemMapper menuItemMapper) {
        this.restaurantScheduleMapper = restaurantScheduleMapper;
        this.menuItemMapper = menuItemMapper;
    }

    public Restaurant mapToDomain(RestaurantInputDto restaurantInputDto) {
        return new Restaurant(
                restaurantInputDto.name(),
                restaurantInputDto.address(),
                restaurantInputDto.typeKitchen(),
                restaurantScheduleMapper.mapToDomain(restaurantInputDto.restaurantSchedule()),
                restaurantInputDto.owner(),
                restaurantInputDto.menuItems() != null ?
                        restaurantInputDto.menuItems().stream().map(menuItemMapper::mapToDomain).toList() :
                        null
        );
    }

    public RestaurantOutputDto mapToOutputDto(Restaurant restaurant) {
        return new RestaurantOutputDto(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getTypeKitchen(),
                restaurantScheduleMapper.mapToDto(restaurant.getRestaurantSchedule()),
                restaurant.getOwner(),
                restaurant.getMenuItems() != null ?
                        restaurant.getMenuItems().stream().map(menuItemMapper::mapToOutputDto).toList() :
                        null
        );
    }
}
