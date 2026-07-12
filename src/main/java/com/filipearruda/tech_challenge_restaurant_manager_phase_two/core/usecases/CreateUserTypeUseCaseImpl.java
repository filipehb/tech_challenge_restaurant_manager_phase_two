package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;

public class CreateUserTypeUseCaseImpl implements CreateUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public CreateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public Long create(UserType userType) {
        return userTypeGateway.create(userType);
    }
}
