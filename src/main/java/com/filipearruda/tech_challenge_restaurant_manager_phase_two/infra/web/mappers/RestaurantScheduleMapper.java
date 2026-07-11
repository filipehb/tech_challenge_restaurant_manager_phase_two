package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantScheduleDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.RestaurantScheduleJson;
import jakarta.validation.Valid;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantScheduleMapper {
    public RestaurantScheduleDto mapToDto(@Valid RestaurantScheduleJson restaurantScheduleJson) {
        Map<DayOfWeek, List<RestaurantScheduleDto.TimeWindowDto>> weeklyHours = restaurantScheduleJson.weeklyHours().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(timeWindow -> new RestaurantScheduleDto.TimeWindowDto(timeWindow.startHour(), timeWindow.endHour()))
                                .toList()
                ));
        return new RestaurantScheduleDto(weeklyHours);
    }

    public RestaurantScheduleJson mapToJson(RestaurantScheduleDto restaurantScheduleDto) {
        Map<DayOfWeek, List<RestaurantScheduleJson.TimeWindowJson>> weeklyHours = restaurantScheduleDto.weeklyHours().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(timeWindow -> new RestaurantScheduleJson.TimeWindowJson(timeWindow.startHour(), timeWindow.endHour()))
                                .toList()
                ));
        return new RestaurantScheduleJson(weeklyHours);
    }
}
