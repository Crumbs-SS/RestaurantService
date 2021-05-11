package com.crumbs.fss.controller;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.service.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    RestaurantSearchService restaurantSearchService;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(@RequestParam(required = false) String query){
        return (query == null || query.isEmpty()) ? restaurantSearchService.getRestaurants()
            : restaurantSearchService.getRestaurantsByQuery(query);
    }

    @GetMapping("/menuitems")
    public List<MenuItem> getFoodByQuery(@RequestParam(required = false) String query){
        return (query == null || query.isEmpty()) ? restaurantSearchService.getMenuItems()
                : restaurantSearchService.getMenuItemsByQuery(query);
    }
}
