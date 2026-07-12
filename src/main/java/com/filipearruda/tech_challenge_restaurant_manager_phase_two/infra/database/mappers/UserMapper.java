package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserTypeMapper userTypeMapper;

    public User mapToDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getName(),
                userEntity.getUserTypeEntity() != null
                        ? userTypeMapper.mapToDomain(userEntity.getUserTypeEntity())
                        : null
        );
    }

    public UserEntity mapToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .userTypeEntity(user.getUserType() != null
                        ? userTypeMapper.mapToEntity(user.getUserType())
                        : null)
                .build();
    }
}
