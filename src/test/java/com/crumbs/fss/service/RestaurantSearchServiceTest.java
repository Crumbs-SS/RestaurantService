package com.crumbs.fss.service;

import com.crumbs.fss.MockUtil;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantSearchServiceTest {

    @Autowired RestaurantSearchService restaurantSearchService;

    @MockBean RestaurantRepository restaurantRepository;
    @MockBean MenuItemRepository menuItemRepository;

    @Test
    void getRestaurants() {
        Mockito.when(restaurantRepository.findAll()).thenReturn(MockUtil.getRestaurants());
        assertEquals(restaurantSearchService.getRestaurants().size(), MockUtil.getRestaurants().size());
    }

    @Test
    void getMenuItems() {
        Mockito.when(menuItemRepository.findAll()).thenReturn(MockUtil.getMenuItems());
        assertEquals(restaurantSearchService.getMenuItems().size(), MockUtil.getMenuItems().size());
    }
}