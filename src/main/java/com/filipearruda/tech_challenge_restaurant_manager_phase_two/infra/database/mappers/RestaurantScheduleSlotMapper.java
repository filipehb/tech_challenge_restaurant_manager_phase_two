package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.RestaurantSchedule;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.RestaurantScheduleSlotEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantScheduleSlotMapper {
    public RestaurantSchedule mapToDomain(List<RestaurantScheduleSlotEntity> slots) {
        RestaurantSchedule restaurantSchedule = new RestaurantSchedule();
        if (slots != null) {
            slots.forEach(slot ->
                    restaurantSchedule.addSchedule(slot.getDayOfWeek(), slot.getStartHour(), slot.getEndHour())
            );
        }
        return restaurantSchedule;
    }

    public RestaurantSchedule mapToDomain(RestaurantScheduleSlotEntity slot) {
        return mapToDomain(slot == null ? List.of() : List.of(slot));
    }

    public List<RestaurantScheduleSlotEntity> mapToEntity(RestaurantSchedule restaurantSchedule) {
        if (restaurantSchedule == null || restaurantSchedule.weeklyHours() == null) {
            return List.of();
        }
        return restaurantSchedule.weeklyHours().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(window -> RestaurantScheduleSlotEntity.builder()
                                .dayOfWeek(entry.getKey())
                                .startHour(window.startHour())
                                .endHour(window.endHour())
                                .build()))
                .toList();
    }
}
