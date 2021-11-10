package com.crumbs.restaurantservice.controller;


import com.crumbs.restaurantservice.dto.DistanceInformation;
import com.crumbs.restaurantservice.service.RestaurantSearchService;
import com.crumbs.lib.entity.Category;
import com.crumbs.lib.entity.MenuItem;
import com.crumbs.lib.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Validated
@PreAuthorize("isAuthenticated()")
@RequestMapping("/restaurant-service")
public class RestaurantSearchServiceController {

    private final RestaurantSearchService restaurantSearchService;

    RestaurantSearchServiceController(
            RestaurantSearchService restaurantSearchService
    ){
        this.restaurantSearchService =  restaurantSearchService;
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantSearchService.findRestaurant(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/restaurants")
    public ResponseEntity<Page<Restaurant>> getRestaurants(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "45") Integer maxDistance,
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @RequestParam(required = false) String[] filter
    ) {
        DistanceInformation distanceInformation = DistanceInformation.builder()
                .customerLat(lat).customerLng(lng).maxDistance(maxDistance).build();

        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 5, sortBy, order);
        Page<Restaurant> restaurants = restaurantSearchService.getRestaurants(distanceInformation, "", pageRequest);
        if (filter != null && filter.length > 0)
            restaurants = restaurantSearchService.filterRestaurantResults(null, filter, pageRequest);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/restaurants/search")
    public ResponseEntity<Page<Restaurant>> getRestaurants(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "45") Integer maxDistance,
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @RequestParam(required = false) String[] filter
    ) {
        DistanceInformation distanceInformation = DistanceInformation.builder()
                .customerLat(lat).customerLng(lng).maxDistance(maxDistance).build();

        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 5, sortBy, order);
        Page<Restaurant> restaurants = restaurantSearchService.getRestaurants(distanceInformation, query, pageRequest);
        if (filter != null && filter.length > 0)
            restaurants = restaurantSearchService.filterRestaurantResults(query, filter, pageRequest);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
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

    @PreAuthorize("permitAll()")
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

    @PreAuthorize("permitAll()")
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = restaurantSearchService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


}
