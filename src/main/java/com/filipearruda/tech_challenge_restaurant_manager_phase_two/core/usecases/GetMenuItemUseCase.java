package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;

public interface GetMenuItemUseCase {
    MenuItem get(Long menuItemId);
}
