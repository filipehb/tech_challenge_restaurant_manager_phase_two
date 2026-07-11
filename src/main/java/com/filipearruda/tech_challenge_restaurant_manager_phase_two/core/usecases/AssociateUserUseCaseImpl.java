package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.UserNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.UserTypeNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;

public class AssociateUserUseCaseImpl implements AssociateUserUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public AssociateUserUseCaseImpl(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public Long associateUserToUserType(Long userId, Long userTypeId) {
        User user = userGateway.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        UserType userType = userTypeGateway.findById(userTypeId)
                .orElseThrow(() -> new UserTypeNotFoundException("UserType not found with id: " + userTypeId));

        user.setUserType(userType);

        return userGateway.update(user);
    }
}
