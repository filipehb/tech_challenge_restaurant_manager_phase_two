package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTypeGatewayImpl implements UserTypeGateway {
    @Override
    public Optional<UserType> findById(Long userTypeId) {
        return Optional.empty();
    }

    @Override
    public Long create(UserType userType) {
        return 0L;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserType update(Long id, UserType userType) {
        return null;
    }
}
