package com.crumbs.fss.service;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.entity.*;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
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

    public Restaurant addRestaurant(addRestaurantDTO a) {

        UserDetail userDetail = UserDetail.builder()
                .firstName(a.getOwnerFirstName())
                .lastName(a.getOwnerLastName())
                .email(a.getOwnerEmail())
                .build();

        RestaurantOwner restaurantOwner = RestaurantOwner.builder()
                .userDetail(userDetail)
                .build();

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
        restaurant.setCategories(restaurantCategories);


        userDetailRepository.save(userDetail);
        restaurantOwnerRepository.save(restaurantOwner);
        locationRepository.save(location);

        return restaurantRepository.save(restaurant);

    }
}
