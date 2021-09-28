package com.crumbs.fss.controller;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.DTO.updateRestaurantDTO;
import com.crumbs.fss.service.RestaurantSearchService;
import com.crumbs.fss.service.RestaurantService;
import com.crumbs.lib.entity.Restaurant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/restaurant-service")
@PreAuthorize("isAuthenticated()")
public class RestaurantServiceController {

    private final RestaurantService restaurantService;

    RestaurantServiceController(
            RestaurantService restaurantService
    ){
        this.restaurantService = restaurantService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @GetMapping("/owner/{username}/restaurants")
    public List<Restaurant> getOwnerRestaurants(@PathVariable String username){
        return restaurantService.getOwnerRestaurants(username);
    }
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @PostMapping("/owner/{username}/restaurant")
    public Restaurant addRestaurant(@PathVariable String username, @Valid @RequestBody addRestaurantDTO aAddRestaurantDTO)  {
        return restaurantService.addRestaurant(username, aAddRestaurantDTO);
    }
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @PutMapping("/owner/{username}/restaurant/{id}")
    public Restaurant updateRestaurant(@PathVariable String username, @PathVariable Long id, @Valid @RequestBody updateRestaurantDTO restaurantDTO){
        return restaurantService.updateRestaurant(username, id, restaurantDTO);
    }
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('OWNER') and #username == authentication.principal)")
    @DeleteMapping("owner/{username}/restaurant/{id}")
    public Restaurant requestDeleteRestaurant(@PathVariable String username, @PathVariable Long id){
        return restaurantService.requestDeleteRestaurant(username, id);
    }
}
