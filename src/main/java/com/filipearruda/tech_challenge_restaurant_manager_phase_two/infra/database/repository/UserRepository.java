package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
