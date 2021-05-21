package com.crumbs.fss.service;

import com.crumbs.fss.MockUtil;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    RestaurantSearchService restaurantSearchService;

    @MockBean RestaurantRepository restaurantRepository;
    @MockBean MenuItemRepository menuItemRepository;

    @Test
    void getRestaurants() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Mockito.when(restaurantRepository.findAll(pageRequest)).thenReturn(MockUtil.getPageRestaurants());
        assertEquals(restaurantSearchService.getRestaurants(pageRequest).get().getNumberOfElements(), MockUtil.getRestaurants().size());
    }

    @Test
    void getMenuItems() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Mockito.when(menuItemRepository.findAll(pageRequest)).thenReturn(MockUtil.getMenuItemsPage());
        assertEquals(restaurantSearchService.getMenuItems(pageRequest).get().getNumberOfElements(), MockUtil.getMenuItems().size());
    }
}