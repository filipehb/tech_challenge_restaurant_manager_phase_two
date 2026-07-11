package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.MenuItemMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.CreateMenuItemUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.DeleteMenuItemUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.GetMenuItemUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.UpdateMenuItemUseCase;

public class MenuItemController {
    private final MenuItemMapper menuItemMapper;
    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final GetMenuItemUseCase getMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;

    public MenuItemController(MenuItemMapper menuItemMapper, CreateMenuItemUseCase createMenuItemUseCase, GetMenuItemUseCase getMenuItemUseCase, DeleteMenuItemUseCase deleteMenuItemUseCase, UpdateMenuItemUseCase updateMenuItemUseCase) {
        this.menuItemMapper = menuItemMapper;
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.getMenuItemUseCase = getMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
    }

    public Long create(MenuItemInputDto menuItemInputDto) {
        MenuItem menuItem = menuItemMapper.mapToDomain(menuItemInputDto);
        return createMenuItemUseCase.create(menuItem);
    }

    public void delete(Long menuItemId) {
        deleteMenuItemUseCase.delete(menuItemId);
    }

    public MenuItemOutputDto get(Long menuItemId) {
        MenuItem menuItem = getMenuItemUseCase.get(menuItemId);
        return menuItemMapper.mapToOutputDto(menuItem);
    }

    public MenuItemOutputDto update(Long menuItemId, MenuItemInputDto menuItemInputDto) {
        MenuItem menuItem = menuItemMapper.mapToDomain(menuItemInputDto);
        MenuItem updated = updateMenuItemUseCase.update(menuItemId, menuItem);
        return menuItemMapper.mapToOutputDto(updated);
    }
}
