package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.UserTypeGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserTypeEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers.UserTypeMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTypeGatewayImpl implements UserTypeGateway {
    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    @Override
    public Optional<UserType> findById(Long userTypeId) {
        Optional<UserTypeEntity> optionalUserType = userTypeRepository.findById(userTypeId);
        if (optionalUserType.isEmpty()) {
            log.error("UserType not found with id: {}", userTypeId);
            return Optional.empty();
        }

        UserType userType = userTypeMapper.mapToDomain(optionalUserType.get());
        return Optional.ofNullable(userType);
    }

    @Override
    public Long create(UserType userType) {
        UserTypeEntity userTypeEntity = userTypeRepository.save(userTypeMapper.mapToEntity(userType));
        return userTypeEntity.getId();
    }

    @Override
    public void delete(Long id) {
        userTypeRepository.deleteById(id);
    }

    @Override
    public UserType update(Long userTypeId, UserType userType) {
        Optional<UserTypeEntity> optionalUserType = userTypeRepository.findById(userTypeId);
        if (optionalUserType.isEmpty()) {
            log.error("UserType not found with id: {}", userTypeId);
            return null;
        }

        optionalUserType.get().setName(userType.getName());

        return userTypeMapper.mapToDomain(userTypeRepository.save(optionalUserType.get()));
    }
}
