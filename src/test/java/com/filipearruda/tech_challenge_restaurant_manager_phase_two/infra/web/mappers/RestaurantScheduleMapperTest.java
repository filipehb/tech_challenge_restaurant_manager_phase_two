package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantScheduleDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.RestaurantScheduleJson;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestaurantScheduleMapperTest {

    private final RestaurantScheduleMapper mapper = new RestaurantScheduleMapper();

    @Test
    void mapToDto_ShouldMapCorrectly() {
        RestaurantScheduleJson json = new RestaurantScheduleJson(Map.of(
                DayOfWeek.MONDAY, List.of(new RestaurantScheduleJson.TimeWindowJson(LocalTime.of(8, 0), LocalTime.of(12, 0))),
                DayOfWeek.TUESDAY, List.of(new RestaurantScheduleJson.TimeWindowJson(LocalTime.of(14, 0), LocalTime.of(18, 0)))
        ));

        RestaurantScheduleDto dto = mapper.mapToDto(json);

        assertNotNull(dto);
        assertEquals(2, dto.weeklyHours().size());
        assertEquals(LocalTime.of(8, 0), dto.weeklyHours().get(DayOfWeek.MONDAY).getFirst().startHour());
        assertEquals(LocalTime.of(14, 0), dto.weeklyHours().get(DayOfWeek.TUESDAY).getFirst().startHour());
    }

    @Test
    void mapToJson_ShouldMapCorrectly() {
        RestaurantScheduleDto dto = new RestaurantScheduleDto(Map.of(
                DayOfWeek.MONDAY, List.of(new RestaurantScheduleDto.TimeWindowDto(LocalTime.of(8, 0), LocalTime.of(12, 0))),
                DayOfWeek.TUESDAY, List.of(new RestaurantScheduleDto.TimeWindowDto(LocalTime.of(14, 0), LocalTime.of(18, 0)))
        ));

        RestaurantScheduleJson json = mapper.mapToJson(dto);

        assertNotNull(json);
        assertEquals(2, json.weeklyHours().size());
        assertEquals(LocalTime.of(8, 0), json.weeklyHours().get(DayOfWeek.MONDAY).getFirst().startHour());
        assertEquals(LocalTime.of(14, 0), json.weeklyHours().get(DayOfWeek.TUESDAY).getFirst().startHour());
    }
}
