package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserTypeMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.CreateUserTypeUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.DeleteUserTypeUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.GetUserTypeUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.UpdateUserTypeUseCase;

public class UserTypeController {
    private final UserTypeMapper userTypeMapper;
    private final CreateUserTypeUseCase createUserTypeUseCase;
    private final GetUserTypeUseCase getUserTypeUseCase;
    private final DeleteUserTypeUseCase deleteUserTypeUseCase;
    private final UpdateUserTypeUseCase updateUserTypeUseCase;

    public UserTypeController(UserTypeMapper userTypeMapper, CreateUserTypeUseCase createUserTypeUseCase, GetUserTypeUseCase getUserTypeUseCase, DeleteUserTypeUseCase deleteUserTypeUseCase, UpdateUserTypeUseCase updateUserTypeUseCase) {
        this.userTypeMapper = userTypeMapper;
        this.createUserTypeUseCase = createUserTypeUseCase;
        this.getUserTypeUseCase = getUserTypeUseCase;
        this.deleteUserTypeUseCase = deleteUserTypeUseCase;
        this.updateUserTypeUseCase = updateUserTypeUseCase;
    }

    public Long create(UserTypeInputDto userTypeInputDto) {
        UserType userType = userTypeMapper.mapToDomain(userTypeInputDto);
        return createUserTypeUseCase.create(userType);
    }

    public void delete(Long userTypeId) {
        deleteUserTypeUseCase.delete(userTypeId);
    }

    public UserTypeOutputDto get(Long userTypeId) {
        UserType userType = getUserTypeUseCase.get(userTypeId);
        return userTypeMapper.mapToOutputDto(userType);
    }

    public UserTypeOutputDto update(Long userTypeId, UserTypeInputDto userTypeInputDto) {
        UserType userType = userTypeMapper.mapToDomain(userTypeInputDto);
        UserType updated = updateUserTypeUseCase.update(userTypeId, userType);
        return userTypeMapper.mapToOutputDto(updated);
    }
}
