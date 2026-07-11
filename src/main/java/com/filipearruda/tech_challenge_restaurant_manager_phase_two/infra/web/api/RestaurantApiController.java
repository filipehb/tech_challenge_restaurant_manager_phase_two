package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.api;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers.RestaurantController;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.RestaurantJson;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers.RestaurantMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantApiController {
    private RestaurantController restaurantController;
    private RestaurantMapper restaurantMapper;

    @PostMapping
    public Long createRestaurant(@Valid @RequestBody RestaurantJson restaurantJson) {
        return restaurantController.create(restaurantMapper.mapToDto(restaurantJson));
    }

    @DeleteMapping(value = "/{restaurantId}")
    public HttpEntity<Object> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantController.delete(restaurantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{restaurantId}")
    public ResponseEntity<RestaurantJson> getRestaurant(@PathVariable Long restaurantId) {
        RestaurantJson restaurantJson = restaurantMapper.mapToJson(restaurantController.get(restaurantId));
        return new ResponseEntity<>(restaurantJson, HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantJson> updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantJson restaurantJson) {
        RestaurantInputDto restaurantInputDto = restaurantMapper.mapToDto(restaurantJson);
        RestaurantOutputDto update = restaurantController.update(restaurantId, restaurantInputDto);
        RestaurantJson updatedRestaurantJson = restaurantMapper.mapToJson(update);
        return new ResponseEntity<>(updatedRestaurantJson, HttpStatus.NO_CONTENT);
    }
}
