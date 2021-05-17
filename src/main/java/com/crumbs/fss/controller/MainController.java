package com.crumbs.fss.controller;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.service.RestaurantSearchService;
import com.crumbs.fss.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MainController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantSearchService restaurantSearchService;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(){
        return restaurantSearchService.getRestaurants();
    }

    @GetMapping("/restaurants/search")
    public List<Restaurant> getRestaurants(@RequestParam(required = false) String query){
        return restaurantSearchService.getRestaurants(query);
    }

    @GetMapping("/menuitems")
    public List<MenuItem> getMenuItems(){
        return restaurantSearchService.getMenuItems();
    }

    @GetMapping("/menuitems/search")
    public List<MenuItem> getMenuItems(@RequestParam(required = false) String query){
        return restaurantSearchService.getMenuItems(query);
    }

    @PostMapping("/restaurants")
    public Restaurant addRestaurant(@RequestBody addRestaurantDTO aRestaurantDTO)  {
       return restaurantService.addRestaurant(aRestaurantDTO);
    }
}
