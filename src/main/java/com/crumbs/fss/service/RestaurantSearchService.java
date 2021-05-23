package com.crumbs.fss.service;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = { Exception.class })
public class RestaurantSearchService {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;

    public Optional<Page<Restaurant>> getRestaurants(PageRequest pageRequest){
        try{
            return Optional.of(restaurantRepository.findAll(pageRequest));
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Page<MenuItem>> getMenuItems(PageRequest pageRequest){
        try{
            return Optional.of(menuItemRepository.findAll(pageRequest));
        } catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Page<Restaurant>> getRestaurants(String query, PageRequest pageRequest) {
        try {
            ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withIgnorePaths("address");

            Example<Restaurant> example = Example.of(Restaurant.builder().name(query).build(),
                    customExampleMatcher);

            return Optional.of(restaurantRepository.findAll(example, pageRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Page<MenuItem>> getMenuItems(String query, PageRequest pageRequest){
        try{
            ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withIgnorePaths("price", "quantity", "description");

            Example<MenuItem> example = Example.of(MenuItem.builder().name(query).build(),
                    customExampleMatcher);

            return Optional.of(menuItemRepository.findAll(example, pageRequest));
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public PageRequest getPageRequest(Integer pageNumber, Integer elements, String sortBy){
        return PageRequest.of(pageNumber, elements, Sort.by(sortBy));
    }
}
