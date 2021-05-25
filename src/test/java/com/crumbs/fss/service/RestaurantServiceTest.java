package com.crumbs.fss.service;

import com.crumbs.fss.ExceptionHandling.DuplicateEmailException;
import com.crumbs.fss.ExceptionHandling.DuplicateLocationException;
import com.crumbs.fss.MockUtil;
import com.crumbs.fss.entity.*;
import com.crumbs.fss.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;


import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
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
    void addRestaurantSuccessfully(){

        Mockito.when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class))).
                thenReturn(MockUtil.getRestaurant());
        Mockito.when(userDetailRepository.save(ArgumentMatchers.any(UserDetail.class))).
                thenReturn(MockUtil.getUserDetail());
        Mockito.when(restaurantOwnerRepository.save(ArgumentMatchers.any(RestaurantOwner.class))).
                thenReturn(MockUtil.getRestaurantOwner());
        Mockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class))).
                thenReturn(MockUtil.getCategory());
        Mockito.when(restaurantCategoryRepository.save(ArgumentMatchers.any(RestaurantCategory.class))).
                thenReturn(MockUtil.getRestaurantCategory());

        Restaurant temp = restaurantService.addRestaurant(MockUtil.getAddRestaurantDTO());
        assertThat(temp).isNotNull();
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void addRestaurantShouldThrowDuplicateEmailException(){
        String email ="a";
        Mockito.when(userDetailRepository.findUserByEmail(anyString()))
                .thenReturn(email);
        assertThrows(DuplicateEmailException.class,()->{
            restaurantService.addRestaurant(MockUtil.getAddRestaurantDTO());
        });
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }
    @Test
    void addRestaurantShouldThrowDuplicateLocationException(){
        String street ="a";
        Mockito.when(locationRepository.findLocationByStreet(anyString()))
                .thenReturn(street);
        assertThrows(DuplicateLocationException.class,()->{
            restaurantService.addRestaurant(MockUtil.getAddRestaurantDTO());
        });
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }
    @Test
    void deleteRestaurantSuccessfully(){
        Mockito.when(restaurantRepository.findById(MockUtil.getRestaurant().getId()))
                .thenReturn(Optional.of(MockUtil.getRestaurant()));

        restaurantService.deleteRestaurant(MockUtil.getRestaurant().getId());
        verify(restaurantRepository).deleteById(MockUtil.getRestaurant().getId());

    }
    @Test
    void deleteRestaurantShouldThrowErrorWhenIDNotFound(){
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class, ()->{
            restaurantService.deleteRestaurant(MockUtil.getRestaurant().getId());
        });

    }
    @Test
    void updateRestaurantSuccessfully(){
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(MockUtil.getRestaurant()));
        Mockito.when(userDetailRepository.findUserByEmail(anyString()))
                .thenReturn(null);
        Mockito.when(locationRepository.findLocationByStreet(anyString()))
                .thenReturn(null);
        restaurantService.updateRestaurant(MockUtil.getRestaurant().getId(), MockUtil.getUpdateRestaurantDTO());
        verify(restaurantRepository).save(MockUtil.getRestaurant());
        verify(restaurantRepository).findById(MockUtil.getRestaurant().getId());
    }
    @Test
    void updateRestaurantShouldThrowEntityNotFoundException(){
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class,()->{
            restaurantService.updateRestaurant(1l,MockUtil.getUpdateRestaurantDTO());
        });
    }
    @Test
    void UpdateRestaurantShouldThrowDuplicateEmailException(){
        String email ="a";
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(MockUtil.getRestaurant()));
        Mockito.when(userDetailRepository.findUserByEmail(anyString()))
                .thenReturn(email);
        assertThrows(DuplicateEmailException.class,()->{
            restaurantService.updateRestaurant(1l,MockUtil.getUpdateRestaurantDTO());
        });
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

}