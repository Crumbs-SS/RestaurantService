package com.crumbs.fss.controller;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.service.RestaurantService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@Validated
public class MainController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(@RequestParam(required = false) String query){
        return (query == null || query.isEmpty()) ? restaurantService.getRestaurants()
            : restaurantService.getRestaurantsByQuery(query);
    }

    @GetMapping("/menuItems")
    public List<MenuItem> getMenuItems(@RequestParam(required = false) String query){
        return (query == null || query.isEmpty()) ? restaurantService.getMenuItems()
                : restaurantService.getMenuItemsByQuery(query);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody addRestaurantDTO aRestaurantDTO)  {
        Restaurant restaurant = restaurantService.addRestaurant(aRestaurantDTO);
        return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
    }
}
