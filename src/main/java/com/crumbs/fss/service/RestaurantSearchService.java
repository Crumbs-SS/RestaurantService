package com.crumbs.fss.service;

import com.crumbs.lib.entity.Category;
import com.crumbs.lib.entity.MenuItem;
import com.crumbs.lib.entity.Restaurant;
import com.crumbs.lib.repository.CategoryRepository;
import com.crumbs.lib.repository.MenuItemRepository;
import com.crumbs.lib.repository.RestaurantRepository;
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

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    RestaurantSearchService(
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository,
            CategoryRepository categoryRepository
    ){
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<MenuItem> getMenuItems(PageRequest pageRequest, Long restaurantId){
        return Optional.of(menuItemRepository.findAllByRestaurantId(restaurantId, pageRequest))
                .orElseThrow();
    }

    public Page<Restaurant> getMenuItems(String query, PageRequest pageRequest) {
        return Optional.of(restaurantRepository.findRestaurantsByMenuItem(query, pageRequest))
                .orElseThrow();
    }

    public Page<Restaurant> getRestaurants(PageRequest pageRequest){
        return Optional.of(restaurantRepository.findAll(pageRequest)).orElseThrow();
    }

    public Page<Restaurant> getRestaurants(String query, PageRequest pageRequest) {
        return Optional.of(restaurantRepository.findAll(getRestaurantExample(query), pageRequest))
                .orElseThrow();
    }

    public Restaurant findRestaurant(Long restaurantId){
        return restaurantRepository.findById(restaurantId).orElseThrow();
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public PageRequest getPageRequest(Integer pageNumber, Integer elements, String sortBy, String order){
        Sort by = ("asc".equals(order)) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
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
