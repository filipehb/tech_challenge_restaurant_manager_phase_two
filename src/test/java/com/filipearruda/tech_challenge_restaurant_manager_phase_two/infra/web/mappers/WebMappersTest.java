package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.TypeKitchen;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.*;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebMappersTest {

    private MenuItemMapper menuItemMapper;
    private UserTypeMapper userTypeMapper;
    private UserMapper userMapper;
    private RestaurantScheduleMapper restaurantScheduleMapper;
    private RestaurantMapper restaurantMapper;

    @BeforeEach
    void setUp() {
        menuItemMapper = new MenuItemMapper();
        userTypeMapper = new UserTypeMapper();
        userMapper = new UserMapper(userTypeMapper);
        restaurantScheduleMapper = new RestaurantScheduleMapper();
        restaurantMapper = new RestaurantMapper(restaurantScheduleMapper, menuItemMapper);
    }

    @Test
    void menuItemMapper_ShouldMapBothWays() {
        MenuItemJson json = new MenuItemJson("Feijoada", 45.9, "Completa", "/img.jpg", true);

        MenuItemInputDto dto = menuItemMapper.mapToDto(json);
        MenuItemJson mapped = menuItemMapper.mapToJson(
                new MenuItemOutputDto(dto.name(), dto.price(), dto.description(), dto.image(), dto.availableOnlyOnSite())
        );

        assertEquals("Feijoada", dto.name());
        assertTrue(mapped.availableOnlyOnSite());
    }

    @Test
    void userTypeMapper_ShouldMapBothWays() {
        UserTypeJson json = new UserTypeJson("OWNER");

        UserTypeInputDto dto = userTypeMapper.mapToDto(json);
        UserTypeJson mapped = userTypeMapper.mapToJson(new UserTypeOutputDto(dto.name()));

        assertEquals("OWNER", dto.name());
        assertEquals("OWNER", mapped.name());
    }

    @Test
    void userMapper_ShouldMapBothWays() {
        UserJson json = new UserJson("user@mail.com", "secret", "User", null);

        UserInputDto dto = userMapper.mapToDto(json);
        UserJson mapped = userMapper.mapToJson(new UserOutputDto(dto.email(), dto.name(), null));

        assertEquals("user@mail.com", dto.email());
        assertNull(mapped.password());
        assertNull(mapped.userType());
    }

    @Test
    void userMapper_ShouldMapWithUserType() {
        UserJson mapped = userMapper.mapToJson(
                new UserOutputDto("user@mail.com", "User", new UserTypeOutputDto("OWNER"))
        );

        assertEquals("OWNER", mapped.userType().name());
    }

    @Test
    void restaurantMapper_ShouldMapToDtoAndJson() {
        Map<DayOfWeek, List<RestaurantScheduleJson.TimeWindowJson>> weeklyHours = new EnumMap<>(DayOfWeek.class);
        weeklyHours.put(DayOfWeek.MONDAY, List.of(
                new RestaurantScheduleJson.TimeWindowJson(LocalTime.of(8, 0), LocalTime.of(12, 0))
        ));
        RestaurantJson json = new RestaurantJson(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                new RestaurantScheduleJson(weeklyHours),
                10L,
                List.of(new MenuItemJson("Feijoada", 45.9, "Completa", "/img.jpg", false))
        );

        RestaurantInputDto dto = restaurantMapper.mapToDto(json);
        RestaurantJson mapped = restaurantMapper.mapToJson(new RestaurantOutputDto(
                dto.name(),
                dto.address(),
                dto.typeKitchen(),
                dto.restaurantSchedule(),
                dto.owner(),
                List.of(new MenuItemOutputDto("Feijoada", 45.9, "Completa", "/img.jpg", false))
        ));

        assertEquals("Cantina", dto.name());
        assertEquals(1, dto.menuItems().size());
        assertEquals("Cantina", mapped.name());
        assertEquals(1, mapped.menuItems().size());
    }

    @Test
    void restaurantMapper_ShouldHandleNullMenuItems() {
        RestaurantJson json = new RestaurantJson(
                "Cantina",
                "Rua 1",
                TypeKitchen.GHOST_KITCHEN,
                new RestaurantScheduleJson(Map.of()),
                10L,
                null
        );

        RestaurantInputDto dto = restaurantMapper.mapToDto(json);
        RestaurantJson mapped = restaurantMapper.mapToJson(new RestaurantOutputDto(
                dto.name(),
                dto.address(),
                dto.typeKitchen(),
                dto.restaurantSchedule(),
                dto.owner(),
                null
        ));

        assertNull(dto.menuItems());
        assertNull(mapped.menuItems());
    }
}
