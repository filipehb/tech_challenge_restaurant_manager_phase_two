package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantScheduleDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.TypeKitchen;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.RestaurantJson;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.RestaurantScheduleJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantMapperTest {

    @Mock
    private RestaurantScheduleMapper restaurantScheduleMapper;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private RestaurantMapper restaurantMapper;

    @Test
    void mapToJson_ShouldMapCorrectly() {
        RestaurantScheduleDto scheduleDto = new RestaurantScheduleDto(Map.of());
        RestaurantOutputDto outputDto = new RestaurantOutputDto(
                "Test Restaurant",
                "123 Street",
                TypeKitchen.COMMERCIAL,
                scheduleDto,
                1L,
                null
        );

        RestaurantScheduleJson scheduleJson = new RestaurantScheduleJson(Map.of());
        when(restaurantScheduleMapper.mapToJson(scheduleDto)).thenReturn(scheduleJson);

        RestaurantJson json = restaurantMapper.mapToJson(outputDto);

        assertNotNull(json);
        assertEquals("Test Restaurant", json.name());
        assertEquals("123 Street", json.address());
        assertEquals(TypeKitchen.COMMERCIAL, json.typeKitchen());
        assertEquals(scheduleJson, json.restaurantSchedule());
        assertEquals(1L, json.owner());
    }
}
