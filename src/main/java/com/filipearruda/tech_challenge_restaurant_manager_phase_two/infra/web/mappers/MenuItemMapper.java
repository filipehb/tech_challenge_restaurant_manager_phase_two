package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.MenuItemJson;
import jakarta.validation.Valid;

public class MenuItemMapper {
    public MenuItemInputDto mapToDto(@Valid MenuItemJson menuItemJson) {
        return new MenuItemInputDto(
                menuItemJson.name(),
                menuItemJson.price(),
                menuItemJson.description(),
                menuItemJson.image(),
                menuItemJson.availableOnlyOnSite()
        );
    }

    public MenuItemJson mapToJson(MenuItemOutputDto menuItemOutputDto) {
        return new MenuItemJson(
                menuItemOutputDto.name(),
                menuItemOutputDto.price(),
                menuItemOutputDto.description(),
                menuItemOutputDto.image(),
                menuItemOutputDto.availableOnlyOnSite()
        );
    }
}
