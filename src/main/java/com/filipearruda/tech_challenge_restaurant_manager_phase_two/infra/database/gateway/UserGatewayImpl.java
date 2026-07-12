package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers.UserMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(Long userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            log.error("User not found with id: {}", userId);
            return Optional.empty();
        }

        return Optional.ofNullable(userMapper.mapToDomain(optionalUser.get()));
    }

    @Override
    public Long update(User user) {
        return 0L;
    }

    @Override
    public Long create(User user) {
        return 0L;
    }
}
