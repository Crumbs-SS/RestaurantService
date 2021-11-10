package com.crumbs.restaurantservice.controller;

import com.crumbs.restaurantservice.dto.AddRestaurantDto;
import com.crumbs.restaurantservice.dto.UpdateRestaurantDto;
import com.crumbs.restaurantservice.service.RestaurantService;
import com.crumbs.lib.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/restaurant-service")
@PreAuthorize("isAuthenticated()")
public class RestaurantServiceController {

    private final RestaurantService restaurantService;


    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @GetMapping("/owner/{username}/restaurants")
    public List<Restaurant> getOwnerRestaurants(@PathVariable String username){
        return restaurantService.getOwnerRestaurants(username);
    }
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @PostMapping("/owner/{username}/restaurant")
    public Restaurant addRestaurant(@PathVariable String username, @Valid @RequestBody AddRestaurantDto aAddRestaurantDto)  {
        return restaurantService.addRestaurant(username, aAddRestaurantDto);
    }
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @PutMapping("/owner/{username}/restaurant/{id}")
    public Restaurant updateRestaurant(@PathVariable String username, @PathVariable Long id, @Valid @RequestBody UpdateRestaurantDto restaurantDTO){
        return restaurantService.updateRestaurant(username, id, restaurantDTO);
    }
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @DeleteMapping("/owner/{username}/restaurant/{id}")
    public Restaurant requestDeleteRestaurant(@PathVariable String username, @PathVariable Long id){
        return restaurantService.requestDeleteRestaurant(username, id);
    }
}
