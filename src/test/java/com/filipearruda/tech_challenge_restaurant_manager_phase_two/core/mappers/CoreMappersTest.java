package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.*;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoreMappersTest {

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
        MenuItemInputDto input = new MenuItemInputDto("Feijoada", 45.9, "Completa", "/img.jpg", false);

        MenuItem domain = menuItemMapper.mapToDomain(input);
        MenuItemOutputDto output = menuItemMapper.mapToOutputDto(domain);

        assertEquals("Feijoada", domain.getName());
        assertEquals(45.9, domain.getPrice());
        assertEquals("Feijoada", output.name());
        assertFalse(output.availableOnlyOnSite());
    }

    @Test
    void userTypeMapper_ShouldMapBothWays() {
        UserTypeInputDto input = new UserTypeInputDto("OWNER");

        UserType domain = userTypeMapper.mapToDomain(input);
        UserTypeOutputDto output = userTypeMapper.mapToOutputDto(domain);

        assertEquals("OWNER", domain.getName());
        assertEquals("OWNER", output.name());
    }

    @Test
    void userMapper_ShouldMapDomainWithoutUserType() {
        UserInputDto input = new UserInputDto("user@mail.com", "secret", "User");

        User domain = userMapper.mapToDomain(input);
        UserOutputDto output = userMapper.mapToOutputDto(domain);

        assertEquals("user@mail.com", domain.getEmail());
        assertEquals("User", output.name());
        assertNull(output.userType());
    }

    @Test
    void userMapper_ShouldMapDomainWithUserType() {
        User user = new User(1L, "user@mail.com", "secret", "User", new UserType(2L, "OWNER"));

        UserOutputDto output = userMapper.mapToOutputDto(user);

        assertEquals("OWNER", output.userType().name());
    }

    @Test
    void restaurantScheduleMapper_ShouldMapBothWays() {
        Map<DayOfWeek, List<RestaurantScheduleDto.TimeWindowDto>> weeklyHours = new EnumMap<>(DayOfWeek.class);
        weeklyHours.put(DayOfWeek.MONDAY, List.of(
                new RestaurantScheduleDto.TimeWindowDto(LocalTime.of(8, 0), LocalTime.of(12, 0))
        ));
        RestaurantScheduleDto dto = new RestaurantScheduleDto(weeklyHours);

        RestaurantSchedule domain = restaurantScheduleMapper.mapToDomain(dto);
        RestaurantScheduleDto mapped = restaurantScheduleMapper.mapToDto(domain);

        assertEquals(1, domain.weeklyHours().get(DayOfWeek.MONDAY).size());
        assertEquals(LocalTime.of(8, 0), mapped.weeklyHours().get(DayOfWeek.MONDAY).getFirst().startHour());
    }

    @Test
    void restaurantScheduleMapper_ShouldHandleNullDto() {
        RestaurantSchedule domain = restaurantScheduleMapper.mapToDomain(null);
        assertTrue(domain.weeklyHours().isEmpty());
    }

    @Test
    void restaurantMapper_ShouldMapBothWays() {
        RestaurantInputDto input = new RestaurantInputDto(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                new RestaurantScheduleDto(Map.of()),
                10L,
                List.of(new MenuItemInputDto("Feijoada", 45.9, "Completa", "/img.jpg", false))
        );

        Restaurant domain = restaurantMapper.mapToDomain(input);
        RestaurantOutputDto output = restaurantMapper.mapToOutputDto(domain);

        assertEquals("Cantina", domain.getName());
        assertEquals(1, domain.getMenuItems().size());
        assertEquals("Cantina", output.name());
        assertEquals(1, output.menuItems().size());
    }

    @Test
    void restaurantMapper_ShouldHandleNullMenuItems() {
        RestaurantInputDto input = new RestaurantInputDto(
                "Cantina",
                "Rua 1",
                TypeKitchen.FOOD_TRUCK,
                new RestaurantScheduleDto(Map.of()),
                10L,
                null
        );

        Restaurant domain = restaurantMapper.mapToDomain(input);

        assertNotNull(domain.getMenuItems());
        assertTrue(domain.getMenuItems().isEmpty());
    }
}
