package com.crumbs.fss.service;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = { Exception.class })
public class RestaurantSearchService {
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired MenuItemRepository menuItemRepository;

    public List<Restaurant> getRestaurants(){
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantsByQuery(String query){
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("address");

        Restaurant restaurant = Restaurant.builder().name(query).build();

        Example<Restaurant> example = Example.of(restaurant, customExampleMatcher);
        return restaurantRepository.findAll(example);
    }

    public List<MenuItem> getMenuItemsByQuery(String query){
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("price", "quantity", "description");

        MenuItem menuItem = MenuItem.builder().name(query).build();

        Example<MenuItem> example = Example.of(menuItem, customExampleMatcher);
        return menuItemRepository.findAll(example);
    }

    public List<MenuItem> getMenuItems(){
        return menuItemRepository.findAll();
    }
}
