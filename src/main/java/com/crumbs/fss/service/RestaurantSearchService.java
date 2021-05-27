package com.crumbs.fss.service;

import com.crumbs.fss.entity.Category;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.CategoryRepository;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = { Exception.class })
public class RestaurantSearchService {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;
    @Autowired
    CategoryRepository categoryRepository;

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

    public PageRequest getPageRequest(Integer pageNumber, Integer elements, String sortBy, String order){
        Sort by = (order.equals("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return sortBy!=null ? PageRequest.of(pageNumber, elements, by) : PageRequest.of(pageNumber, elements);
    }

    public Optional<List<Category>> getCategories(){
        try{
            return Optional.of(categoryRepository.findAll());
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Page<Restaurant> filterResults(String[] filter, PageRequest pageRequest) {
        List<Restaurant> restaurants = restaurantRepository.findAll(pageRequest.getSort());
        List<Restaurant> restaurantContent = restaurants.stream().filter(restaurant -> {
            List<Boolean> state = new ArrayList<>();

            for (String option : filter)
                state.add(restaurant.getCategories().stream()
                        .anyMatch(v -> v.getCategory().getName().equals(option)));

            return state.stream().allMatch(v -> v);
        }).collect(Collectors.toList());

        return new PageImpl<>(restaurantContent, pageRequest, restaurantContent.size());
    }
}
