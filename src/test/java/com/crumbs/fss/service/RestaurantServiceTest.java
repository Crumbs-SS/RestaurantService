package com.crumbs.fss.service;

import com.crumbs.fss.exception.DuplicateLocationException;
import com.crumbs.fss.MockUtil;
import com.crumbs.lib.entity.*;
import com.crumbs.lib.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class RestaurantServiceTest {

    @Autowired
    RestaurantService restaurantService;
    @MockBean
    RestaurantRepository restaurantRepository;
    @MockBean
    UserDetailsRepository userDetailRepository;
    @MockBean
    LocationRepository locationRepository;
    @MockBean
    CategoryRepository categoryRepository;
    @MockBean
    RestaurantCategoryRepository restaurantCategoryRepository;
    @MockBean
    RestaurantStatusRepository restaurantStatusRepository;

    @Test
    void getOwnerRestaurants(){

        Mockito.when(userDetailRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(MockUtil.getUserDetail()) );
        List<Restaurant> restaurants = restaurantService.getOwnerRestaurants("testUsername");
        assertEquals(restaurants.size(), MockUtil.getRestaurants().size());
    }

    @Test
    void addRestaurantSuccessfully(){

        Mockito.when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class))).
                thenReturn(MockUtil.getRestaurant());
        Mockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class))).
                thenReturn(MockUtil.getCategory());
        Mockito.when(restaurantCategoryRepository.save(ArgumentMatchers.any(RestaurantCategory.class))).
                thenReturn(MockUtil.getRestaurantCategory());

        Mockito.when(userDetailRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(MockUtil.getUserDetail()) );
        Mockito.when(restaurantStatusRepository.findById("REGISTERED")).thenReturn(Optional.of(MockUtil.getRegisteredStatus()));
        Restaurant temp = restaurantService.addRestaurant("testUsername",MockUtil.getAddRestaurantDTO());
        assertThat(temp).isNotNull();
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void addRestaurantShouldThrowDuplicateLocationException(){

        Mockito.when(locationRepository.findLocationByStreet(anyString())).thenReturn("mockStreet");
        Mockito.when(userDetailRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(MockUtil.getUserDetail()) );
        assertThrows(DuplicateLocationException.class,()-> restaurantService.addRestaurant("testUsername", MockUtil.getAddRestaurantDTO()));
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurantSuccessfully() {

        Mockito.when(userDetailRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(MockUtil.getUserDetail()) );
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(MockUtil.getRestaurant()));
        Mockito.when(locationRepository.findLocationByStreet(anyString())).thenReturn(null);
        Mockito.when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class))).thenReturn(MockUtil.getRestaurant());
        restaurantService.updateRestaurant("testUsername", MockUtil.getRestaurant().getId(), MockUtil.getUpdateRestaurantDTO());
        verify(restaurantRepository).save(any(Restaurant.class));
        verify(restaurantRepository).findById(MockUtil.getRestaurant().getId());
    }

    @Test
    void updateRestaurantShouldThrowEntityNotFoundException() {

        Mockito.when(userDetailRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(MockUtil.getUserDetail()) );
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> restaurantService.updateRestaurant("testUsername", 1l, MockUtil.getUpdateRestaurantDTO()));
    }

    @Test
    void requestDeleteRestaurant(){

        Mockito.when(userDetailRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(MockUtil.getUserDetail()) );
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(MockUtil.getRestaurant()));
        Mockito.when(restaurantStatusRepository.findById("PENDING_DELETE")).thenReturn(Optional.of(MockUtil.getPendingDeleteStatus()));
        Mockito.when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class))).thenReturn(MockUtil.getRestaurant());
        assertEquals(restaurantService.requestDeleteRestaurant("testUsername", MockUtil.getRestaurant().getId()).getRestaurantStatus(), MockUtil.getPendingDeleteStatus());
        verify(restaurantRepository).save(any(Restaurant.class));

    }

}

