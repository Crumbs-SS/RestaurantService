package com.crumbs.fss.controller;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.service.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantSearch")
public class MainController {

    @Autowired
    RestaurantSearchService restaurantSearchService;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(@RequestParam(required = false) String name){
        return (name == null || name.isEmpty()) ? restaurantSearchService.getRestaurants()
            : restaurantSearchService.getRestaurantsByQuery(name);
    }

    @GetMapping("/menuitems")
    public List<MenuItem> getFoodByQuery(@PathVariable String query){
        return restaurantSearchService.getMenuItemsByQuery(query);
    }
}
