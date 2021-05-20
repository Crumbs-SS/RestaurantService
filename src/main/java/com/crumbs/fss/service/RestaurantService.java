package com.crumbs.fss.service;

import com.crumbs.fss.DTO.RestaurantDTO;
import com.crumbs.fss.ExceptionHandling.DuplicateEmailException;
import com.crumbs.fss.ExceptionHandling.DuplicateLocationException;
import com.crumbs.fss.entity.*;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = { Exception.class })
public class RestaurantService {

    @Autowired RestaurantRepository restaurantRepository;
    @Autowired MenuItemRepository menuItemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired LocationRepository locationRepository;
    @Autowired RestaurantCategoryRepository restaurantCategoryRepository;
    @Autowired RestaurantOwnerRepository restaurantOwnerRepository;
    @Autowired UserDetailRepository userDetailRepository;

    public List<Restaurant> getRestaurants(){
        return restaurantRepository.findAll();
    }

    public List<MenuItem> getMenuItems(){
        return menuItemRepository.findAll();
    }

    public List<Restaurant> getRestaurantsByQuery(String query){
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("address");

        Example<Restaurant> example = Example.of(Restaurant.builder().name(query).build(),
                customExampleMatcher);

        return restaurantRepository.findAll(example);
    }

    public List<MenuItem> getMenuItemsByQuery(String query){
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("price", "description");

        Example<MenuItem> example = Example.of(MenuItem.builder().name(query).build(),
                customExampleMatcher);
 
        return menuItemRepository.findAll(example);
    }
    public Restaurant addRestaurant(RestaurantDTO a) {

        if(userDetailRepository.findUserByEmail(a.getOwnerEmail())!=null)
            throw new DuplicateEmailException();

        UserDetail userDetail = UserDetail.builder()
                .firstName(a.getOwnerFirstName())
                .lastName(a.getOwnerLastName())
                .email(a.getOwnerEmail())
                .build();

        RestaurantOwner restaurantOwner = RestaurantOwner.builder()
                .userDetail(userDetail)
                .build();

        if(locationRepository.findLocationByStreet(a.getStreet())!=null)
            throw new DuplicateLocationException();

        Location location = Location.builder()
                .street(a.getStreet())
                .city(a.getCity())
                .zipCode(a.getZip())
                .state(a.getState())
                .build();

        Restaurant temp = Restaurant.builder()
                .name(a.getName())
                .priceRating(a.getPriceRating())
                .location(location)
                .restaurantOwner(restaurantOwner)
                .build();

        Restaurant restaurant = restaurantRepository.save(temp);

        List<Category> categories = a.getCategories();
        List<RestaurantCategory> restaurantCategories = new ArrayList<>();

        if(categories!= null) {
            categories.forEach(category -> {
                categoryRepository.save(category);

                RestaurantCategoryID resCatID = RestaurantCategoryID.builder()
                        .restaurantId(restaurant.getId())
                        .categoryId(category.getName())
                        .build();

                //create restaurant category
                RestaurantCategory resCat = RestaurantCategory.builder()
                        .id(resCatID)
                        .restaurant(restaurant)
                        .category(category)
                        .build();

                restaurantCategories.add(resCat);

            });
        }
        restaurant.setCategories(restaurantCategories);


        userDetailRepository.save(userDetail);
        restaurantOwnerRepository.save(restaurantOwner);
        locationRepository.save(location);

        return restaurantRepository.save(restaurant);

    }
    public void deleteRestaurant(Long id){

        Restaurant temp = restaurantRepository.findById(id).get();
        restaurantRepository.deleteById(id);
        locationRepository.deleteById(temp.getLocation().getId());
        restaurantOwnerRepository.deleteById(temp.getRestaurantOwner().getId());
        userDetailRepository.deleteById(temp.getRestaurantOwner().getUserDetail().getId());
        if(menuItemRepository.findById(id).isPresent())
            menuItemRepository.deleteById(id);
    }
    public Restaurant updateRestaurant(Long id, RestaurantDTO restaurantDTO){
        //throw exception if null
        Restaurant temp = restaurantRepository.findById(id).get();
        


       return restaurantRepository.save(temp);
    }


}
