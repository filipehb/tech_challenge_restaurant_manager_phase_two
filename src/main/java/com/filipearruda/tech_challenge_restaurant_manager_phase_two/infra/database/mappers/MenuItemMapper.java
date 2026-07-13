package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.MenuItemEntity;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
    public MenuItem mapToDomain(MenuItemEntity menuItemEntity) {
        return new MenuItem(
                menuItemEntity.getId(),
                menuItemEntity.getName(),
                menuItemEntity.getPrice(),
                menuItemEntity.getDescription(),
                menuItemEntity.getImage(),
                menuItemEntity.isAvailableOnlyOnSite()
        );
    }

    public MenuItemEntity mapToEntity(MenuItem menuItem) {
        return MenuItemEntity.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .image(menuItem.getImage())
                .availableOnlyOnSite(menuItem.isAvailableOnlyOnSite())
                .build();
    }
}
