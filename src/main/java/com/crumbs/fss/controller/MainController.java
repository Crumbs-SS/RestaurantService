package com.crumbs.fss.controller;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.entity.Category;
import com.crumbs.fss.DTO.updateRestaurantDTO;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.service.RestaurantSearchService;
import com.crumbs.fss.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    RestaurantSearchService restaurantSearchService;

    @GetMapping("/restaurants")
    public ResponseEntity<Page<Restaurant>> getRestaurants(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String[] filter
    ){

        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 5, sortBy, order);
        Page<Restaurant> restaurants = restaurantSearchService.getRestaurants(pageRequest)
                .orElseThrow();
        if (filter != null && filter.length > 0)
            restaurants = restaurantSearchService.filterResults(filter, pageRequest);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/restaurants/search")
    public ResponseEntity<Page<Restaurant>> getRestaurants(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String[] filter
    ){
        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 5, sortBy, order);
        Page<Restaurant> restaurants = restaurantSearchService.getRestaurants(query, pageRequest)
                .orElseThrow();
        if (filter != null && filter.length > 0)
            restaurants = restaurantSearchService.filterResults(filter, pageRequest);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/menuitems")
    public ResponseEntity<Page<MenuItem>> getMenuItems(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 10, sortBy, order);
        Page<MenuItem> menuItems = restaurantSearchService.getMenuItems(pageRequest)
                .orElseThrow();

        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    @GetMapping("/menuitems/search")
    public ResponseEntity<Page<MenuItem>> getMenuItems(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ){
        PageRequest pageRequest = restaurantSearchService.getPageRequest(page, 10, sortBy, order);
        Page<MenuItem> menuItems = restaurantSearchService.getMenuItems(query, pageRequest)
                .orElseThrow();
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }
    @GetMapping("/restaurantss")
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }
    @GetMapping("/restaurants/{id}")
    public List<Restaurant> getRestaurantOwnerRestaurants(@PathVariable Long id){
        return restaurantService.getRestaurantOwnerRestaurants(id);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = restaurantSearchService.getCategories().orElseThrow();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/restaurants")
    public Restaurant addRestaurant(@Valid @RequestBody addRestaurantDTO aAddRestaurantDTO)  {
        return restaurantService.addRestaurant(aAddRestaurantDTO);
    }
    @PutMapping("/restaurants/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @Valid @RequestBody updateRestaurantDTO restaurantDTO){
        return restaurantService.updateRestaurant(id, restaurantDTO);
    }
    @DeleteMapping("/restaurants/{id}")
    public Restaurant deleteRestaurant(@PathVariable Long id){
        return restaurantService.deleteRestaurant(id);
    }

    @DeleteMapping("/restaurantOwnerRestaurant/{id}")
    public Restaurant deleteRestaurantOwnerRestaurant(@PathVariable Long id){return restaurantService.deleteRestaurantOwnerRestaurant(id);}
}
