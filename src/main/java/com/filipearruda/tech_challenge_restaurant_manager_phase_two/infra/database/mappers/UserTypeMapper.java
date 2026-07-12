package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTypeMapper {
    public UserType mapToDomain(UserTypeEntity userTypeEntity) {
        return new UserType(
                userTypeEntity.getId(),
                userTypeEntity.getName()
        );
    }
}
