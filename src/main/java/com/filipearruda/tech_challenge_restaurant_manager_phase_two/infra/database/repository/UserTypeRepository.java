package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
}
