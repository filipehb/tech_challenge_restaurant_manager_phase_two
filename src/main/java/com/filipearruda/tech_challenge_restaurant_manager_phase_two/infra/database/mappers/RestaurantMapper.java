package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.RestaurantEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.RestaurantScheduleSlotEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {
    private final RestaurantScheduleSlotMapper restaurantScheduleSlotMapper;
    private final MenuItemMapper menuItemMapper;

    public Restaurant mapToDomain(RestaurantEntity restaurantEntity) {
        return new Restaurant(
                restaurantEntity.getName(),
                restaurantEntity.getAddress(),
                restaurantEntity.getTypeKitchen(),
                restaurantScheduleSlotMapper.mapToDomain(restaurantEntity.getScheduleSlots()),
                restaurantEntity.getOwner(),
                restaurantEntity.getMenuItems() != null
                        ? restaurantEntity.getMenuItems().stream().map(menuItemMapper::mapToDomain).toList()
                        : List.of()
        );
    }

    public RestaurantEntity mapToEntity(Restaurant restaurant) {
        List<RestaurantScheduleSlotEntity> scheduleSlots =
                restaurantScheduleSlotMapper.mapToEntity(restaurant.getRestaurantSchedule());
        return RestaurantEntity.builder()
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .typeKitchen(restaurant.getTypeKitchen())
                .scheduleSlots(scheduleSlots.isEmpty() ? null : scheduleSlots)
                .owner(restaurant.getOwner())
                .menuItems(restaurant.getMenuItems() != null
                        ? restaurant.getMenuItems().stream().map(menuItemMapper::mapToEntity).toList()
                        : List.of())
                .build();
    }
}
