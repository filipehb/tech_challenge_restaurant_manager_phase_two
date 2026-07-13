package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.UserMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.AssociateUserUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private AssociateUserUseCase associateUserUseCase;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userMapper, createUserUseCase, associateUserUseCase);
    }

    @Test
    void shouldCreateUser() {
        UserInputDto input = new UserInputDto("user@mail.com", "secret", "User");
        User domain = mock(User.class);
        when(userMapper.mapToDomain(input)).thenReturn(domain);
        when(createUserUseCase.create(domain)).thenReturn(1L);

        assertEquals(1L, userController.create(input));
    }

    @Test
    void shouldAssociateUserToUserType() {
        User domain = mock(User.class);
        UserOutputDto output = mock(UserOutputDto.class);
        when(associateUserUseCase.associateUserToUserType(1L, 2L)).thenReturn(domain);
        when(userMapper.mapToOutputDto(domain)).thenReturn(output);

        assertEquals(output, userController.associateUserToUserType(1L, 2L));
    }
}
