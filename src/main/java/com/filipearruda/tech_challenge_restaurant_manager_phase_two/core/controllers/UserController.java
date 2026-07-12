package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.AssociateUserUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.CreateUserUseCase;

public class UserController {
    private final UserMapper userMapper;
    private final CreateUserUseCase createUserUseCase;
    private final AssociateUserUseCase associateUserUseCase;

    public UserController(
            UserMapper userMapper,
            CreateUserUseCase createUserUseCase,
            AssociateUserUseCase associateUserUseCase) {
        this.userMapper = userMapper;
        this.createUserUseCase = createUserUseCase;
        this.associateUserUseCase = associateUserUseCase;
    }

    public Long create(UserInputDto userInputDto) {
        User user = userMapper.mapToDomain(userInputDto);
        return createUserUseCase.create(user);
    }

    public UserOutputDto associateUserToUserType(Long userId, Long userTypeId) {
        return userMapper.mapToOutputDto(associateUserUseCase.associateUserToUserType(userId, userTypeId));
    }
}
