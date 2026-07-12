package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.UserTypeNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;

public class GetUserTypeUseCaseImpl implements GetUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public GetUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType get(Long userTypeId) {
        return userTypeGateway.findById(userTypeId).orElseThrow(() -> new UserTypeNotFoundException("UserType not found with id: " + userTypeId));
    }
}
