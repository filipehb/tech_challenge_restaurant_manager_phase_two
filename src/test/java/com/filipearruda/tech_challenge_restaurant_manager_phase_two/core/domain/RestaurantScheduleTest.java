package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantScheduleTest {

    @Test
    void shouldProtectEncapsulation() {
        RestaurantSchedule schedule = new RestaurantSchedule();
        schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));

        assertThrows(UnsupportedOperationException.class, () -> {
            schedule.weeklyHours().clear();
        });
    }

    @Test
    void shouldNotAllowInvalidTimeWindow() {
        RestaurantSchedule schedule = new RestaurantSchedule();

        assertThrows(ValidationException.class, () -> {
            schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(8, 0));
        });
    }

    @Test
    void shouldAllowValidTimeWindow() {
        RestaurantSchedule schedule = new RestaurantSchedule();
        schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));

        assertEquals(1, schedule.weeklyHours().get(DayOfWeek.MONDAY).size());
    }

    @Test
    void shouldNotAllowOverlappingTimeWindows() {
        RestaurantSchedule schedule = new RestaurantSchedule();
        schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));

        assertThrows(ValidationException.class, () ->
                schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(14, 0))
        );
    }

    @Test
    void shouldAllowNonOverlappingTimeWindowsOnSameDay() {
        RestaurantSchedule schedule = new RestaurantSchedule();
        schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
        schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0));

        assertEquals(2, schedule.weeklyHours().get(DayOfWeek.MONDAY).size());
    }

    @Test
    void shouldAllowAdjacentTimeWindows() {
        RestaurantSchedule schedule = new RestaurantSchedule();
        schedule.addSchedule(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
        schedule.addSchedule(DayOfWeek.TUESDAY, LocalTime.of(12, 0), LocalTime.of(18, 0));

        assertEquals(2, schedule.weeklyHours().get(DayOfWeek.TUESDAY).size());
    }

    @Test
    void shouldNotAllowNullStartHour() {
        assertThrows(NullPointerException.class, () ->
                new RestaurantSchedule.TimeWindow(null, LocalTime.of(12, 0))
        );
    }

    @Test
    void shouldNotAllowNullEndHour() {
        assertThrows(NullPointerException.class, () ->
                new RestaurantSchedule.TimeWindow(LocalTime.of(8, 0), null)
        );
    }
}
