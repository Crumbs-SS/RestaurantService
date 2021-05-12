package com.crumbs.fss.service;

import com.crumbs.fss.MockUtil;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @MockBean RestaurantRepository restaurantRepository;
    @MockBean MenuItemRepository menuItemRepository;

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
}