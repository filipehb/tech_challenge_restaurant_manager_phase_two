package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public record RestaurantScheduleJson(
        Map<DayOfWeek, List<TimeWindowJson>> weeklyHours
) {
    public record TimeWindowJson(LocalTime startHour, LocalTime endHour) {
    }
}
