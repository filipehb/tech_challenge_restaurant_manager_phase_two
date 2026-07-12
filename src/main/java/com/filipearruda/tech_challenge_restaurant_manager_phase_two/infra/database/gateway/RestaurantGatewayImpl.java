package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.RestaurantGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.RestaurantEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.RestaurantScheduleSlotEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers.RestaurantMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers.RestaurantScheduleSlotMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantGatewayImpl implements RestaurantGateway {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantScheduleSlotMapper restaurantScheduleSlotMapper;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Long create(Restaurant restaurant) {
        RestaurantEntity savedRestaurantEntity = restaurantRepository.save(restaurantMapper.mapToEntity(restaurant));
        return savedRestaurantEntity.getId();
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        Optional<RestaurantEntity> optionalRestaurantEntity = restaurantRepository.findById(id);
        if (optionalRestaurantEntity.isEmpty()) {
            log.error("Restaurant not found with id: {}", id);
            return Optional.empty();
        }
        Restaurant restaurant = restaurantMapper.mapToDomain(optionalRestaurantEntity.get());
        return Optional.ofNullable(restaurant);
    }

    @Override
    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public Restaurant update(Long id, Restaurant restaurant) {
        Optional<RestaurantEntity> entity = restaurantRepository.findById(id);
        if (entity.isEmpty()) {
            log.error("Restaurant not found with id: {}", id);
            return null;
        }

        entity.get().setName(restaurant.getName());
        entity.get().setAddress(restaurant.getAddress());
        entity.get().setOwner(restaurant.getOwner());
        entity.get().setTypeKitchen(restaurant.getTypeKitchen());

        entity.get().getScheduleSlots().clear();
        List<RestaurantScheduleSlotEntity> slots =
                restaurantScheduleSlotMapper.mapToEntity(restaurant.getRestaurantSchedule());
        slots.forEach(slot -> {
            slot.setRestaurant(entity.get());
            entity.get().getScheduleSlots().add(slot);
        });

        RestaurantEntity saved = restaurantRepository.save(entity.get());
        return restaurantMapper.mapToDomain(saved);
    }
}
