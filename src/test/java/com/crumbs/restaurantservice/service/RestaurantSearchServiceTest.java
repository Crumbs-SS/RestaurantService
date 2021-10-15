package com.crumbs.restaurantservice.service;

import com.crumbs.restaurantservice.MockUtil;
import com.crumbs.lib.entity.Category;
import com.crumbs.lib.entity.Restaurant;
import com.crumbs.lib.repository.CategoryRepository;
import com.crumbs.lib.repository.MenuItemRepository;
import com.crumbs.lib.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class RestaurantSearchServiceTest {

    @Autowired RestaurantSearchService restaurantSearchService;

    @MockBean RestaurantRepository restaurantRepository;
    @MockBean MenuItemRepository menuItemRepository;
    @MockBean CategoryRepository categoryRepository;

    @Test
    void getMenuItems() {
        Restaurant restaurant = MockUtil.getRestaurant();
        PageRequest pageRequest = PageRequest.of(0, 5);
        Mockito.when(menuItemRepository.findAllByRestaurantId(restaurant.getId(), pageRequest))
                .thenReturn(MockUtil.getMenuItemsPage());
        assertEquals(restaurantSearchService.getMenuItems(pageRequest, restaurant.getId()).getNumberOfElements(),
                MockUtil.getMenuItemsPage().getNumberOfElements());
    }

    @Test
    void testGetMenuItems() {
        String query = "";
        PageRequest pageRequest = PageRequest.of(0, 5);

        Mockito.when(restaurantRepository.findRestaurantsByMenuItem(query, pageRequest))
                .thenReturn(MockUtil.getPageRestaurants());

        assertEquals(restaurantSearchService.getMenuItems(query, pageRequest).getNumberOfElements(),
                MockUtil.getPageRestaurants().getNumberOfElements());
    }

    @Test
    void getRestaurants() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Mockito.when(restaurantRepository.findAll(pageRequest)).thenReturn(MockUtil.getPageRestaurants());
        assertEquals(restaurantSearchService.getRestaurants(pageRequest).getNumberOfElements(),
                MockUtil.getPageRestaurants().getNumberOfElements());
    }

    @Test
    void testGetRestaurants() {
        String query = "";
        PageRequest pageRequest = PageRequest.of(0, 5);

        Mockito.when(restaurantRepository.findAll(any(Example.class), any(PageRequest.class)))
                .thenReturn(MockUtil.getPageRestaurants());

        assertEquals(restaurantSearchService.getRestaurants(query, pageRequest).getNumberOfElements(),
                MockUtil.getPageRestaurants().getNumberOfElements());
    }

    @Test
    void findRestaurant() {
        Restaurant restaurant = MockUtil.getRestaurant();

        Mockito.when(restaurantRepository.findById(restaurant.getId()))
                .thenReturn(Optional.of(restaurant));

        assertEquals(restaurantSearchService.findRestaurant(restaurant.getId()).getId(),
                restaurant.getId());

    }

    @Test
    void getCategories() {
        List<Category> categories = MockUtil.getCategories();

        Mockito.when(categoryRepository.findAll())
                .thenReturn(categories);

        assertEquals(restaurantSearchService.getCategories().size(),
                categories.size());
    }

    @Test
    void getPageRequest() {
        Integer pageNumber = 1;
        Integer elements = 5;

        assertEquals(5,
                restaurantSearchService.getPageRequest(pageNumber, elements, "id", null).getPageSize());
    }
}