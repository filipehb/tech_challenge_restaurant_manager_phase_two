package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.ValidationException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class RestaurantSchedule {
    private final Map<DayOfWeek, List<TimeWindow>> weeklyHours;

    public RestaurantSchedule() {
        weeklyHours = new EnumMap<>(DayOfWeek.class);
    }

    public Map<DayOfWeek, List<TimeWindow>> weeklyHours() {
        Map<DayOfWeek, List<TimeWindow>> unmodifiableWeeklyHours = new EnumMap<>(DayOfWeek.class);
        weeklyHours.forEach((day, windows) -> unmodifiableWeeklyHours.put(day, List.copyOf(windows)));
        return Collections.unmodifiableMap(unmodifiableWeeklyHours);
    }

    public void addSchedule(DayOfWeek day, LocalTime startHour, LocalTime endHour) {
        List<TimeWindow> dailyWindows = weeklyHours.computeIfAbsent(day, k -> new ArrayList<>());
        TimeWindow newWindow = new TimeWindow(startHour, endHour);

        boolean isOverlapping = isIsOverlapping(dailyWindows, newWindow);

        if (isOverlapping) {
            throw new ValidationException("Schedule overlaps with an existing time window.");
        }

        dailyWindows.add(newWindow);
    }

    private boolean isIsOverlapping(List<TimeWindow> dailyWindows, TimeWindow newWindow) {
        return dailyWindows.stream().anyMatch(existing ->
                newWindow.startHour().isBefore(existing.endHour()) &&
                        newWindow.endHour().isAfter(existing.startHour())
        );
    }

    public record TimeWindow(LocalTime startHour, LocalTime endHour) {
        public TimeWindow {
            Objects.requireNonNull(startHour, "startHour cannot be null");
            Objects.requireNonNull(endHour, "endHour cannot be null");
            if (endHour.isBefore(startHour)) {
                throw new ValidationException("endHour cannot be before startHour");
            }
        }
    }
}
