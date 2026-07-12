package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.api;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.MenuItemController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.MenuItemJson;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.MenuItemMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuItemApiController {
    private final MenuItemController menuItemController;
    private final MenuItemMapper menuItemMapper;

    @PostMapping
    public ResponseEntity<Long> createMenuItem(@Valid @RequestBody MenuItemJson menuItemJson) {
        return new ResponseEntity<>(menuItemController.create(menuItemMapper.mapToDto(menuItemJson)), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{menuItemId}")
    public HttpEntity<Object> deleteMenuItem(@PathVariable Long menuItemId) {
        menuItemController.delete(menuItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{menuItemId}")
    public ResponseEntity<MenuItemJson> getMenuItem(@PathVariable Long menuItemId) {
        MenuItemJson MenuItemJson = menuItemMapper.mapToJson(menuItemController.get(menuItemId));
        return new ResponseEntity<>(MenuItemJson, HttpStatus.OK);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemJson> updateMenuItem(@PathVariable Long menuItemId, @Valid @RequestBody MenuItemJson menuItemJson) {
        MenuItemInputDto menuItemInputDto = menuItemMapper.mapToDto(menuItemJson);
        MenuItemOutputDto update = menuItemController.update(menuItemId, menuItemInputDto);
        MenuItemJson updatedMenuJson = menuItemMapper.mapToJson(update);
        return new ResponseEntity<>(updatedMenuJson, HttpStatus.OK);
    }
}
