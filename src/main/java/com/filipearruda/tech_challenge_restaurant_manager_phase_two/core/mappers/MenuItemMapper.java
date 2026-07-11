package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemOutputDto;

public class MenuItemMapper {
    public MenuItem mapToDomain(MenuItemInputDto menuItemInputDto) {
        return new MenuItem(
                menuItemInputDto.name(),
                menuItemInputDto.price(),
                menuItemInputDto.description(),
                menuItemInputDto.image(),
                menuItemInputDto.availableOnlyOnSite()
        );
    }

    public MenuItemOutputDto mapToOutputDto(MenuItem menuItem) {
        return new MenuItemOutputDto(
                menuItem.getName(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.getImage(),
                menuItem.isAvailableOnlyOnSite()
        );
    }
}
