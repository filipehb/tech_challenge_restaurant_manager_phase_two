package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.MenuItemGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity.MenuItemEntity;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.mappers.MenuItemMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuItemGatewayImpl implements MenuItemGateway {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    @Override
    public Long create(MenuItem menuItem) {
        MenuItemEntity menuItemEntity = menuItemRepository.save(menuItemMapper.mapToEntity(menuItem));
        return menuItemEntity.getId();
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        Optional<MenuItemEntity> optionalMenuItem = menuItemRepository.findById(id);
        if (optionalMenuItem.isEmpty()) {
            log.error("MenuItem not found with id: {}", id);
            return Optional.empty();
        }
        return Optional.ofNullable(menuItemMapper.mapToDomain(optionalMenuItem.get()));
    }

    @Override
    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public MenuItem update(Long id, MenuItem menuItem) {
        Optional<MenuItemEntity> optionalMenuItem = menuItemRepository.findById(id);
        if (optionalMenuItem.isEmpty()) {
            log.error("MenuItem not found with id: {}", id);
            return null;
        }

        optionalMenuItem.get().setName(menuItem.getName());
        optionalMenuItem.get().setPrice(menuItem.getPrice());
        optionalMenuItem.get().setDescription(menuItem.getDescription());
        optionalMenuItem.get().setImage(menuItem.getImage());
        optionalMenuItem.get().setAvailableOnlyOnSite(menuItem.isAvailableOnlyOnSite());
        MenuItemEntity saved = menuItemRepository.save(optionalMenuItem.get());

        return menuItemMapper.mapToDomain(saved);
    }
}
