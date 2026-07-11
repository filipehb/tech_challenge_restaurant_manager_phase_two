package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.RestaurantSchedule;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantScheduleDto;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class RestaurantScheduleMapper {
    public RestaurantSchedule mapToDomain(RestaurantScheduleDto restaurantScheduleDto) {
        RestaurantSchedule restaurantSchedule = new RestaurantSchedule();
        if (restaurantScheduleDto != null && restaurantScheduleDto.weeklyHours() != null) {
            restaurantScheduleDto.weeklyHours().forEach((day, timeWindows) -> {
                timeWindows.forEach(timeWindowDto -> {
                    restaurantSchedule.addSchedule(day, timeWindowDto.startHour(), timeWindowDto.endHour());
                });
            });
        }
        return restaurantSchedule;
    }

    public RestaurantScheduleDto mapToDto(RestaurantSchedule restaurantSchedule) {
        Map<DayOfWeek, List<RestaurantScheduleDto.TimeWindowDto>> weeklyHours = new EnumMap<>(DayOfWeek.class);
        if (restaurantSchedule != null && restaurantSchedule.weeklyHours() != null) {
            restaurantSchedule.weeklyHours().forEach((day, timeWindows) -> {
                List<RestaurantScheduleDto.TimeWindowDto> timeWindowDtos = timeWindows.stream()
                        .map(timeWindow -> new RestaurantScheduleDto.TimeWindowDto(timeWindow.startHour(), timeWindow.endHour()))
                        .toList();
                weeklyHours.put(day, timeWindowDtos);
            });
        }
        return new RestaurantScheduleDto(weeklyHours);
    }
}
