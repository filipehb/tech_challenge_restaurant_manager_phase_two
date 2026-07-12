package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.config;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.MenuItemController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.RestaurantController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.UserController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.UserTypeController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.MenuItemGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.RestaurantGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {
    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.UserTypeMapper webUserTypeMapper() {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.UserTypeMapper();
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.UserMapper webUserMapper(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.UserTypeMapper webUserTypeMapper) {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.UserMapper(webUserTypeMapper);
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.MenuItemMapper webMenuItemMapper() {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.MenuItemMapper();
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.RestaurantScheduleMapper webRestaurantScheduleMapper() {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.RestaurantScheduleMapper();
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.RestaurantMapper webRestaurantMapper(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.RestaurantScheduleMapper webRestaurantScheduleMapper,
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.MenuItemMapper webMenuItemMapper) {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.RestaurantMapper(
                webRestaurantScheduleMapper,
                webMenuItemMapper
        );
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserTypeMapper coreUserTypeMapper() {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserTypeMapper();
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserMapper coreUserMapper(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserTypeMapper coreUserTypeMapper) {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserMapper(coreUserTypeMapper);
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.MenuItemMapper coreMenuItemMapper() {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.MenuItemMapper();
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantScheduleMapper coreRestaurantScheduleMapper() {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantScheduleMapper();
    }

    @Bean
    public com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantMapper coreRestaurantMapper(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantScheduleMapper coreRestaurantScheduleMapper,
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.MenuItemMapper coreMenuItemMapper) {
        return new com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantMapper(
                coreRestaurantScheduleMapper,
                coreMenuItemMapper
        );
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway userGateway) {
        return new CreateUserUseCaseImpl(userGateway);
    }

    @Bean
    public AssociateUserUseCase associateUserUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        return new AssociateUserUseCaseImpl(userGateway, userTypeGateway);
    }

    @Bean
    public CreateUserTypeUseCase createUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new CreateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public GetUserTypeUseCase getUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new GetUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new DeleteUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new UpdateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new CreateRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public GetRestaurantUseCase getRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new GetRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new DeleteRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new UpdateRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new CreateMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public GetMenuItemUseCase getMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new GetMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new DeleteMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new UpdateMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public UserController userController(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserMapper coreUserMapper,
            CreateUserUseCase createUserUseCase,
            AssociateUserUseCase associateUserUseCase) {
        return new UserController(coreUserMapper, createUserUseCase, associateUserUseCase);
    }

    @Bean
    public UserTypeController userTypeController(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserTypeMapper coreUserTypeMapper,
            CreateUserTypeUseCase createUserTypeUseCase,
            GetUserTypeUseCase getUserTypeUseCase,
            DeleteUserTypeUseCase deleteUserTypeUseCase,
            UpdateUserTypeUseCase updateUserTypeUseCase) {
        return new UserTypeController(
                coreUserTypeMapper,
                createUserTypeUseCase,
                getUserTypeUseCase,
                deleteUserTypeUseCase,
                updateUserTypeUseCase
        );
    }

    @Bean
    public RestaurantController restaurantController(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantMapper coreRestaurantMapper,
            CreateRestaurantUseCase createRestaurantUseCase,
            GetRestaurantUseCase getRestaurantUseCase,
            DeleteRestaurantUseCase deleteRestaurantUseCase,
            UpdateRestaurantUseCase updateRestaurantUseCase) {
        return new RestaurantController(
                coreRestaurantMapper,
                createRestaurantUseCase,
                getRestaurantUseCase,
                deleteRestaurantUseCase,
                updateRestaurantUseCase
        );
    }

    @Bean
    public MenuItemController menuItemController(
            com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.MenuItemMapper coreMenuItemMapper,
            CreateMenuItemUseCase createMenuItemUseCase,
            GetMenuItemUseCase getMenuItemUseCase,
            DeleteMenuItemUseCase deleteMenuItemUseCase,
            UpdateMenuItemUseCase updateMenuItemUseCase) {
        return new MenuItemController(
                coreMenuItemMapper,
                createMenuItemUseCase,
                getMenuItemUseCase,
                deleteMenuItemUseCase,
                updateMenuItemUseCase
        );
    }
}
