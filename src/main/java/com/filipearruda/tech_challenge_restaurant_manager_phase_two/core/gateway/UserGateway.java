package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;

import java.util.Optional;

public interface UserGateway {
    Optional<User> findById(Long userId);

    User update(Long userId, User user);

    Long create(User user);
}
