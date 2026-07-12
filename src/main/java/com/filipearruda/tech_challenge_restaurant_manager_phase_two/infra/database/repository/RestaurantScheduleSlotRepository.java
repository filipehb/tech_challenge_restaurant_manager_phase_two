package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.RestaurantScheduleSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantScheduleSlotRepository extends JpaRepository<RestaurantScheduleSlotEntity, Long> {
}
