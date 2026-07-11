package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public record RestaurantScheduleDto(
        Map<DayOfWeek, List<TimeWindowDto>> weeklyHours
) {
    public record TimeWindowDto(LocalTime startHour, LocalTime endHour) {
    }
}
