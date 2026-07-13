package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.*;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseMappersTest {

    private MenuItemMapper menuItemMapper;
    private UserTypeMapper userTypeMapper;
    private UserMapper userMapper;
    private RestaurantScheduleSlotMapper scheduleSlotMapper;
    private RestaurantMapper restaurantMapper;

    @BeforeEach
    void setUp() {
        menuItemMapper = new MenuItemMapper();
        userTypeMapper = new UserTypeMapper();
        userMapper = new UserMapper(userTypeMapper);
        scheduleSlotMapper = new RestaurantScheduleSlotMapper();
        restaurantMapper = new RestaurantMapper(scheduleSlotMapper, menuItemMapper);
    }

    @Test
    void menuItemMapper_ShouldMapBothWays() {
        MenuItem domain = new MenuItem(1L, "Feijoada", 45.9, "Completa", "/img.jpg", false);

        MenuItemEntity entity = menuItemMapper.mapToEntity(domain);
        MenuItem mapped = menuItemMapper.mapToDomain(entity);

        assertEquals(1L, entity.getId());
        assertEquals("Feijoada", mapped.getName());
    }

    @Test
    void userTypeMapper_ShouldMapBothWays() {
        UserType domain = new UserType(1L, "OWNER");

        UserTypeEntity entity = userTypeMapper.mapToEntity(domain);
        UserType mapped = userTypeMapper.mapToDomain(entity);

        assertEquals("OWNER", entity.getName());
        assertEquals(1L, mapped.getId());
    }

    @Test
    void userMapper_ShouldMapWithAndWithoutUserType() {
        User withoutType = new User(1L, "user@mail.com", "secret", "User", null);
        User withType = new User(2L, "owner@mail.com", "secret", "Owner", new UserType(3L, "OWNER"));

        UserEntity entityWithoutType = userMapper.mapToEntity(withoutType);
        UserEntity entityWithType = userMapper.mapToEntity(withType);

        assertNull(entityWithoutType.getUserTypeEntity());
        assertEquals("OWNER", entityWithType.getUserTypeEntity().getName());
        assertEquals("User", userMapper.mapToDomain(entityWithoutType).getName());
        assertEquals("OWNER", userMapper.mapToDomain(entityWithType).getUserType().getName());
    }

    @Test
    void scheduleSlotMapper_ShouldMapBothWays() {
        RestaurantSchedule schedule = new RestaurantSchedule();
        schedule.addSchedule(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));

        List<RestaurantScheduleSlotEntity> entities = scheduleSlotMapper.mapToEntity(schedule);
        RestaurantSchedule mapped = scheduleSlotMapper.mapToDomain(entities);

        assertEquals(1, entities.size());
        assertEquals(1, mapped.weeklyHours().get(DayOfWeek.MONDAY).size());
        assertTrue(scheduleSlotMapper.mapToEntity(null).isEmpty());
        assertTrue(scheduleSlotMapper.mapToDomain((List<RestaurantScheduleSlotEntity>) null).weeklyHours().isEmpty());
    }

    @Test
    void restaurantMapper_ShouldMapBothWays() {
        RestaurantSchedule schedule = new RestaurantSchedule();
        schedule.addSchedule(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(23, 0));
        Restaurant domain = new Restaurant(
                "Cantina",
                "Rua 1",
                TypeKitchen.FAST_CASUAL,
                schedule,
                10L,
                List.of(new MenuItem(1L, "Feijoada", 45.9, "Completa", "/img.jpg", false))
        );

        RestaurantEntity entity = restaurantMapper.mapToEntity(domain);
        entity.setScheduleSlots(new ArrayList<>(entity.getScheduleSlots()));
        entity.setMenuItems(new ArrayList<>(entity.getMenuItems()));
        Restaurant mapped = restaurantMapper.mapToDomain(entity);

        assertEquals("Cantina", entity.getName());
        assertEquals(1, entity.getScheduleSlots().size());
        assertEquals("Cantina", mapped.getName());
        assertEquals(1, mapped.getMenuItems().size());
    }
}
