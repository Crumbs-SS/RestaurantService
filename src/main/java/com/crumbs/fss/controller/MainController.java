package com.crumbs.fss.controller;


import com.crumbs.fss.service.RestaurantSearchService;
import com.crumbs.lib.entity.Category;
import com.crumbs.lib.entity.MenuItem;
import com.crumbs.lib.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Validated
public class MainController {


    @Autowired
    RestaurantSearchService restaurantSearchService;

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantSearchService.findRestaurant(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<Page<Restaurant>> getRestaurants(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String[] filter
    ) {

        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 5, sortBy, order);
        Page<Restaurant> restaurants = restaurantSearchService.getRestaurants(pageRequest);
        if (filter != null && filter.length > 0)
            restaurants = restaurantSearchService.filterRestaurantResults(null, filter, pageRequest);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/restaurants/search")
    public ResponseEntity<Page<Restaurant>> getRestaurants(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String[] filter
    ) {

        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 5, sortBy, order);
        Page<Restaurant> restaurants = restaurantSearchService.getRestaurants(query, pageRequest);
        if (filter != null && filter.length > 0)
            restaurants = restaurantSearchService.filterRestaurantResults(query, filter, pageRequest);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public ResponseEntity<Page<MenuItem>> getMenuItems(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @PathVariable Long restaurantId
    ) {
        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 10, sortBy, order);
        Page<MenuItem> menuItems = restaurantSearchService.getMenuItems(pageRequest, restaurantId);

        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    @GetMapping("/restaurants/menuitems")
    public ResponseEntity<Page<Restaurant>> getMenuItems(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String[] filter

    ) {

        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 5, sortBy, order);
        Page<Restaurant> restaurants = restaurantSearchService.getMenuItems(query, pageRequest);

        if (filter != null && filter.length > 0)
            restaurants = restaurantSearchService.filterMenuRestaurantResults(query, filter, pageRequest);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = restaurantSearchService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
