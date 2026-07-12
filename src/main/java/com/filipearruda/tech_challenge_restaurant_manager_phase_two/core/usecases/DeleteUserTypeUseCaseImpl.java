package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;

public class DeleteUserTypeUseCaseImpl implements DeleteUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public DeleteUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void delete(Long userTypeId) {
        userTypeGateway.delete(userTypeId);
    }
}
