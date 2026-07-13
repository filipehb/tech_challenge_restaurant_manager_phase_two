package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.MenuItemOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.MenuItemMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.CreateMenuItemUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.DeleteMenuItemUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.GetMenuItemUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.UpdateMenuItemUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private MenuItemMapper menuItemMapper;
    @Mock
    private CreateMenuItemUseCase createMenuItemUseCase;
    @Mock
    private GetMenuItemUseCase getMenuItemUseCase;
    @Mock
    private DeleteMenuItemUseCase deleteMenuItemUseCase;
    @Mock
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    private MenuItemController menuItemController;

    @BeforeEach
    void setUp() {
        menuItemController = new MenuItemController(
                menuItemMapper,
                createMenuItemUseCase,
                getMenuItemUseCase,
                deleteMenuItemUseCase,
                updateMenuItemUseCase
        );
    }

    @Test
    void shouldCreateMenuItem() {
        MenuItemInputDto input = new MenuItemInputDto("Feijoada", 45.9, "Completa", "/img.jpg", false);
        MenuItem domain = mock(MenuItem.class);
        when(menuItemMapper.mapToDomain(input)).thenReturn(domain);
        when(createMenuItemUseCase.create(domain)).thenReturn(1L);

        assertEquals(1L, menuItemController.create(input));
    }

    @Test
    void shouldGetMenuItem() {
        MenuItem domain = mock(MenuItem.class);
        MenuItemOutputDto output = mock(MenuItemOutputDto.class);
        when(getMenuItemUseCase.get(1L)).thenReturn(domain);
        when(menuItemMapper.mapToOutputDto(domain)).thenReturn(output);

        assertEquals(output, menuItemController.get(1L));
    }

    @Test
    void shouldDeleteMenuItem() {
        menuItemController.delete(1L);
        verify(deleteMenuItemUseCase).delete(1L);
    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItemInputDto input = new MenuItemInputDto("Feijoada", 45.9, "Completa", "/img.jpg", false);
        MenuItem domain = mock(MenuItem.class);
        MenuItem updated = mock(MenuItem.class);
        MenuItemOutputDto output = mock(MenuItemOutputDto.class);
        when(menuItemMapper.mapToDomain(input)).thenReturn(domain);
        when(updateMenuItemUseCase.update(1L, domain)).thenReturn(updated);
        when(menuItemMapper.mapToOutputDto(updated)).thenReturn(output);

        assertEquals(output, menuItemController.update(1L, input));
    }
}
