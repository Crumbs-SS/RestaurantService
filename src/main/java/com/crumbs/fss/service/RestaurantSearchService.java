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

    public Optional<Page<MenuItem>> getMenuItems(PageRequest pageRequest){
        try{
            return Optional.of(menuItemRepository.findAll(pageRequest));
        } catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Page<MenuItem>> getMenuItems(PageRequest pageRequest, Long restaurantId){
        try{
            return Optional.of(menuItemRepository.findAllByRestaurantId(restaurantId, pageRequest));
        } catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Page<Restaurant>> getMenuItems(String query, PageRequest pageRequest){
        try{
            return Optional.of(restaurantRepository.findRestaurantsByMenuItem(query, pageRequest));
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Page<Restaurant>> getRestaurants(PageRequest pageRequest){
        try{
            return Optional.of(restaurantRepository.findAll(pageRequest));
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Page<Restaurant>> getRestaurants(String query, PageRequest pageRequest) {
        try {
            return Optional.of(restaurantRepository.findAll(getRestaurantExample(query), pageRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<List<Category>> getCategories(){
        try{
            return Optional.of(categoryRepository.findAll());
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public PageRequest getPageRequest(Integer pageNumber, Integer elements, String sortBy, String order){
        Sort by = (order.equals("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return sortBy!=null ? PageRequest.of(pageNumber, elements, by) : PageRequest.of(pageNumber, elements);
    }


    public Page<Restaurant> filterRestaurantResults(String query, String[] filter, PageRequest pageRequest) {
        List<Restaurant> restaurants = query != null ? restaurantRepository.findAll(getRestaurantExample(query),
                pageRequest.getSort()) : restaurantRepository.findAll(pageRequest.getSort());

        List<Restaurant> restaurantContent = getRestaurantFilterResults(restaurants, filter);
        return new PageImpl<>(restaurantContent, pageRequest, restaurantContent.size());
    }

    public Page<Restaurant> filterMenuRestaurantResults(String query, String[] filter, PageRequest pageRequest){
        List<Restaurant> restaurants = query != null ? restaurantRepository.findRestaurantsByMenuItem(query,
                pageRequest.getSort()) : restaurantRepository.findAll(pageRequest.getSort());

        List<Restaurant> restaurantContent = getRestaurantFilterResults(restaurants, filter);
        return new PageImpl<>(restaurantContent, pageRequest, restaurantContent.size());
    }

    private List<Restaurant> getRestaurantFilterResults(List<Restaurant> restaurants, String[] filter){
        return restaurants.stream().filter(restaurant -> {
            List<Boolean> state = new ArrayList<>();

            for (String option : filter)
                state.add(restaurant.getCategories().stream()
                        .anyMatch(v -> v.getCategory().getName().equals(option)));

            return state.stream().allMatch(v -> v);
        }).collect(Collectors.toList());
    }

    private Example<Restaurant> getRestaurantExample(String query){
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("address");

        return Example.of(Restaurant.builder().name(query).build(),
                customExampleMatcher);
    }
}
