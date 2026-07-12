package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;

import java.util.Optional;

public interface UserTypeGateway {
    Optional<UserType> findById(Long userTypeId);

    Long create(UserType userType);

    void delete(Long id);

    UserType update(Long id, UserType userType);
}
