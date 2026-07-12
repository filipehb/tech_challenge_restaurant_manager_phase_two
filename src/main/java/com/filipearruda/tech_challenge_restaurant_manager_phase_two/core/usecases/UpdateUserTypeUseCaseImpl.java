package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;

public class UpdateUserTypeUseCaseImpl implements UpdateUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public UpdateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType update(Long userTypeId, UserType userType) {
        return userTypeGateway.update(userTypeId, userType);
    }
}
