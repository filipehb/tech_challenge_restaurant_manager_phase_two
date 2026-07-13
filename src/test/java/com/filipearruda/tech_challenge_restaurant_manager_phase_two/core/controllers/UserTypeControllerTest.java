package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.UserType;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserTypeMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.CreateUserTypeUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.DeleteUserTypeUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.GetUserTypeUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.UpdateUserTypeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTypeControllerTest {

    @Mock
    private UserTypeMapper userTypeMapper;
    @Mock
    private CreateUserTypeUseCase createUserTypeUseCase;
    @Mock
    private GetUserTypeUseCase getUserTypeUseCase;
    @Mock
    private DeleteUserTypeUseCase deleteUserTypeUseCase;
    @Mock
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    private UserTypeController userTypeController;

    @BeforeEach
    void setUp() {
        userTypeController = new UserTypeController(
                userTypeMapper,
                createUserTypeUseCase,
                getUserTypeUseCase,
                deleteUserTypeUseCase,
                updateUserTypeUseCase
        );
    }

    @Test
    void shouldCreateUserType() {
        UserTypeInputDto input = new UserTypeInputDto("OWNER");
        UserType domain = mock(UserType.class);
        when(userTypeMapper.mapToDomain(input)).thenReturn(domain);
        when(createUserTypeUseCase.create(domain)).thenReturn(1L);

        assertEquals(1L, userTypeController.create(input));
    }

    @Test
    void shouldGetUserType() {
        UserType domain = mock(UserType.class);
        UserTypeOutputDto output = mock(UserTypeOutputDto.class);
        when(getUserTypeUseCase.get(1L)).thenReturn(domain);
        when(userTypeMapper.mapToOutputDto(domain)).thenReturn(output);

        assertEquals(output, userTypeController.get(1L));
    }

    @Test
    void shouldDeleteUserType() {
        userTypeController.delete(1L);
        verify(deleteUserTypeUseCase).delete(1L);
    }

    @Test
    void shouldUpdateUserType() {
        UserTypeInputDto input = new UserTypeInputDto("CUSTOMER");
        UserType domain = mock(UserType.class);
        UserType updated = mock(UserType.class);
        UserTypeOutputDto output = mock(UserTypeOutputDto.class);
        when(userTypeMapper.mapToDomain(input)).thenReturn(domain);
        when(updateUserTypeUseCase.update(1L, domain)).thenReturn(updated);
        when(userTypeMapper.mapToOutputDto(updated)).thenReturn(output);

        assertEquals(output, userTypeController.update(1L, input));
    }
}
