package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.api;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.UserController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.UserJson;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {
    private UserController userController;
    private UserMapper userMapper;

    @PostMapping
    public Long createUser(@Valid @RequestBody UserJson userJson) {
        return userController.create(userMapper.mapToDto(userJson));
    }

    @PostMapping(value = "/associate-user/{userId}/to-type/{userTypeId}")
    public Long associateUserToUserType(@PathVariable Long userTypeId, @PathVariable Long userId) {
        return userController.associateUserToUserType(userId, userTypeId);
    }
}
