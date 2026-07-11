package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import java.util.Optional;

public interface MenuItemGateway {
    Long create(MenuItem menuItem);
    Optional<MenuItem> findById(Long id);
    void delete(Long id);
    MenuItem update(Long id, MenuItem menuItem);
}
