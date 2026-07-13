package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.*;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.MenuItemEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.RestaurantEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserTypeEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers.*;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.MenuItemRepository;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.RestaurantRepository;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.UserRepository;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GatewayImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserTypeRepository userTypeRepository;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private MenuItemMapper menuItemMapper;
    @Mock
    private RestaurantScheduleSlotMapper restaurantScheduleSlotMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserTypeMapper userTypeMapper;

    private RestaurantGatewayImpl restaurantGateway;
    private MenuItemGatewayImpl menuItemGateway;
    private UserGatewayImpl userGateway;
    private UserTypeGatewayImpl userTypeGateway;

    @BeforeEach
    void setUp() {
        restaurantGateway = new RestaurantGatewayImpl(
                restaurantMapper, menuItemMapper, restaurantScheduleSlotMapper, restaurantRepository
        );
        menuItemGateway = new MenuItemGatewayImpl(menuItemRepository, menuItemMapper);
        userGateway = new UserGatewayImpl(userRepository, userMapper, userTypeMapper);
        userTypeGateway = new UserTypeGatewayImpl(userTypeRepository, userTypeMapper);
    }

    @Test
    void restaurantGateway_ShouldCreateAndFind() {
        Restaurant restaurant = new Restaurant(
                "Cantina", "Rua 1", TypeKitchen.FAST_CASUAL, new RestaurantSchedule(), 10L
        );
        RestaurantEntity entity = RestaurantEntity.builder()
                .id(1L)
                .scheduleSlots(new ArrayList<>())
                .menuItems(new ArrayList<>())
                .build();

        when(restaurantScheduleSlotMapper.mapToEntity(restaurant.getRestaurantSchedule())).thenReturn(List.of());
        when(restaurantMapper.mapToEntity(restaurant)).thenReturn(entity);
        when(restaurantRepository.save(entity)).thenReturn(entity);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(restaurantMapper.mapToDomain(entity)).thenReturn(restaurant);

        assertEquals(1L, restaurantGateway.create(restaurant));
        assertTrue(restaurantGateway.findById(1L).isPresent());
        assertTrue(restaurantGateway.findById(2L).isEmpty());
    }

    @Test
    void restaurantGateway_ShouldUpdateAndDelete() {
        Restaurant restaurant = new Restaurant(
                "Cantina", "Rua 1", TypeKitchen.FAST_CASUAL, new RestaurantSchedule(), 10L
        );
        RestaurantEntity entity = RestaurantEntity.builder()
                .id(1L)
                .scheduleSlots(new ArrayList<>())
                .menuItems(new ArrayList<>())
                .build();

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(restaurantScheduleSlotMapper.mapToEntity(restaurant.getRestaurantSchedule())).thenReturn(List.of());
        when(restaurantRepository.save(entity)).thenReturn(entity);
        when(restaurantMapper.mapToDomain(entity)).thenReturn(restaurant);

        assertEquals(restaurant, restaurantGateway.update(1L, restaurant));
        assertNull(restaurantGateway.update(2L, restaurant));

        restaurantGateway.delete(1L);
        verify(restaurantRepository).deleteById(1L);
    }

    @Test
    void menuItemGateway_ShouldCoverCrudPaths() {
        MenuItem menuItem = new MenuItem("Feijoada", 45.9, "Completa", "/img.jpg", false);
        MenuItemEntity entity = MenuItemEntity.builder().id(1L).name("Feijoada").build();

        when(menuItemMapper.mapToEntity(menuItem)).thenReturn(entity);
        when(menuItemRepository.save(any(MenuItemEntity.class))).thenReturn(entity);
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(menuItemMapper.mapToDomain(entity)).thenReturn(menuItem);

        assertEquals(1L, menuItemGateway.create(menuItem));
        assertTrue(menuItemGateway.findById(1L).isPresent());
        assertTrue(menuItemGateway.findById(2L).isEmpty());
        assertEquals(menuItem, menuItemGateway.update(1L, menuItem));
        assertNull(menuItemGateway.update(2L, menuItem));

        menuItemGateway.delete(1L);
        verify(menuItemRepository).deleteById(1L);
    }

    @Test
    void userAndUserTypeGateways_ShouldCoverCrudPaths() {
        UserType userType = new UserType(2L, "OWNER");
        User user = new User(1L, "user@mail.com", "secret", "User", userType);
        UserTypeEntity userTypeEntity = UserTypeEntity.builder().id(2L).name("OWNER").build();
        UserEntity userEntity = UserEntity.builder().id(1L).email("user@mail.com").build();

        when(userTypeMapper.mapToEntity(userType)).thenReturn(userTypeEntity);
        when(userTypeRepository.save(userTypeEntity)).thenReturn(userTypeEntity);
        when(userTypeRepository.findById(2L)).thenReturn(Optional.of(userTypeEntity));
        when(userTypeMapper.mapToDomain(userTypeEntity)).thenReturn(userType);

        when(userMapper.mapToEntity(user)).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapToDomain(userEntity)).thenReturn(user);

        assertEquals(2L, userTypeGateway.create(userType));
        assertTrue(userTypeGateway.findById(2L).isPresent());
        assertTrue(userTypeGateway.findById(9L).isEmpty());
        assertEquals(userType, userTypeGateway.update(2L, userType));
        assertNull(userTypeGateway.update(9L, userType));
        userTypeGateway.delete(2L);

        assertEquals(1L, userGateway.create(user));
        assertTrue(userGateway.findById(1L).isPresent());
        assertTrue(userGateway.findById(9L).isEmpty());
        assertEquals(user, userGateway.update(1L, user));
        assertNull(userGateway.update(9L, user));
    }
}
