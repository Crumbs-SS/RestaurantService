package com.crumbs.fss.controller;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.service.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurantSearch")
public class MainController {

    @Autowired
    RestaurantSearchService restaurantSearchService;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(){
        return restaurantSearchService.getRestaurants();
    }
}
