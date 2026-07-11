package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.api;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.UserTypeController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.UserTypeJson;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.UserTypeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/user-type")
@RequiredArgsConstructor
public class UserTypeApiController {
    private UserTypeController userTypeController;
    private UserTypeMapper userTypeMapper;

    @PostMapping
    public Long createUserType(@Valid @RequestBody UserTypeJson userTypeJson) {
        return userTypeController.create(userTypeMapper.mapToDto(userTypeJson));
    }

    @DeleteMapping(value = "/{userTypeId}")
    public HttpEntity<Object> deleteUserType(@PathVariable Long userTypeId) {
        userTypeController.delete(userTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{userTypeId}")
    public ResponseEntity<UserTypeJson> getUserType(@PathVariable Long userTypeId) {
        UserTypeJson userTypeJson = userTypeMapper.mapToJson(userTypeController.get(userTypeId));
        return new ResponseEntity<>(userTypeJson, HttpStatus.OK);
    }

    @PutMapping("/{userTypeId}")
    public ResponseEntity<UserTypeJson> updateUserType(@PathVariable Long userTypeId, @Valid @RequestBody UserTypeJson userTypeJson) {
        UserTypeInputDto userTypeInputDto = userTypeMapper.mapToDto(userTypeJson);
        UserTypeOutputDto update = userTypeController.update(userTypeId, userTypeInputDto);
        UserTypeJson updatedUserTypeJson = userTypeMapper.mapToJson(update);
        return new ResponseEntity<>(updatedUserTypeJson, HttpStatus.NO_CONTENT);
    }
}
