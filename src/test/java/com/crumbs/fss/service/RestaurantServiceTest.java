package com.crumbs.fss.service;

import com.crumbs.fss.MockUtil;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @MockBean RestaurantRepository restaurantRepository;
    @MockBean MenuItemRepository menuItemRepository;
    @MockBean UserDetailRepository userDetailRepository;
    @MockBean RestaurantOwnerRepository restaurantOwnerRepository;
    @MockBean LocationRepository locationRepository;
    @MockBean CategoryRepository categoryRepository;
    @MockBean RestaurantCategoryRepository restaurantCategoryRepository;


    @Test
    void getRestaurants() {
        Mockito.when(restaurantRepository.findAll()).thenReturn(MockUtil.getRestaurants());
        assertEquals(restaurantService.getRestaurants().size(), MockUtil.getRestaurants().size());
    }

    @Test
    void getMenuItems() {
        Mockito.when(menuItemRepository.findAll()).thenReturn(MockUtil.getMenuItems());
        assertEquals(restaurantService.getMenuItems().size(), MockUtil.getMenuItems().size());
    }
    @Test
    void addRestaurant(){

        Mockito.when(restaurantRepository.save(MockUtil.getRestaurant())).
                thenReturn(MockUtil.getRestaurant());
        Mockito.when(userDetailRepository.save(MockUtil.getUserDetail())).
                thenReturn(MockUtil.getUserDetail());
        Mockito.when(restaurantOwnerRepository.save(MockUtil.getRestaurantOwner())).
                thenReturn(MockUtil.getRestaurantOwner());
        Mockito.when(categoryRepository.save(MockUtil.getCategory())).
                thenReturn(MockUtil.getCategory());
        Mockito.when(restaurantCategoryRepository.save(MockUtil.getRestaurantCategory())).
                thenReturn(MockUtil.getRestaurantCategory());

        ResponseEntity temp = restaurantService.addRestaurant(MockUtil.getAddRestaurantDTO());
        assertThat(temp).isNotNull();
        verify(restaurantRepository).save(any(Restaurant.class));



    }
}