package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;

public interface UpdateUserTypeUseCase {
    UserType update(Long userTypeId, UserType userType);
}
