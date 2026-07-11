package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.RestaurantJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantMapper {
    private final RestaurantScheduleMapper restaurantScheduleMapper;
    private final MenuItemMapper menuItemMapper;

    public RestaurantInputDto mapToDto(@Valid RestaurantJson restaurantJson) {
        return new RestaurantInputDto(
                restaurantJson.name(),
                restaurantJson.address(),
                restaurantJson.typeKitchen(),
                restaurantScheduleMapper.mapToDto(restaurantJson.restaurantSchedule()),
                restaurantJson.owner(),
                restaurantJson.menuItems() != null ?
                        restaurantJson.menuItems().stream().map(menuItemMapper::mapToDto).toList() :
                        null
        );
    }

    public RestaurantJson mapToJson(RestaurantOutputDto restaurantOutputDto) {
        return new RestaurantJson(
                restaurantOutputDto.name(),
                restaurantOutputDto.address(),
                restaurantOutputDto.typeKitchen(),
                restaurantScheduleMapper.mapToJson(restaurantOutputDto.restaurantSchedule()),
                restaurantOutputDto.owner(),
                restaurantOutputDto.menuItems() != null ?
                        restaurantOutputDto.menuItems().stream().map(menuItemMapper::mapToJson).toList() :
                        null
        );
    }
}
