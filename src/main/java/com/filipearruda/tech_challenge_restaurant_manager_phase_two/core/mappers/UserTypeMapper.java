package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeOutputDto;

public class UserTypeMapper {
    public UserType mapToDomain(UserTypeInputDto userTypeInputDto) {
        return new UserType(userTypeInputDto.name());
    }

    public UserTypeOutputDto mapToOutputDto(UserType userType) {
        return new UserTypeOutputDto(userType.getName());
    }
}
