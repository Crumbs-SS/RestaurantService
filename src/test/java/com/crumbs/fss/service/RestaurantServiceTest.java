package com.crumbs.fss.service;

import com.crumbs.fss.ExceptionHandling.DuplicateLocationException;
import com.crumbs.fss.MockUtil;
import com.crumbs.lib.entity.*;
import com.crumbs.lib.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
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
    RestaurantSearchService restaurantSearchService;
    @Autowired
    RestaurantService restaurantService;

    @MockBean
    RestaurantRepository restaurantRepository;
    @MockBean
    MenuItemRepository menuItemRepository;
    @MockBean
    UserDetailsRepository userDetailRepository;
    @MockBean
    RestaurantOwnerRepository restaurantOwnerRepository;
    @MockBean
    LocationRepository locationRepository;
    @MockBean
    CategoryRepository categoryRepository;
    @MockBean
    RestaurantCategoryRepository restaurantCategoryRepository;
    @MockBean
    RestaurantStatusRepository restaurantStatusRepository;


    @Test
    void getRestaurants() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Mockito.when(restaurantRepository.findAll(pageRequest)).thenReturn(MockUtil.getPageRestaurants());
        assertEquals(restaurantSearchService.getRestaurants(pageRequest).getNumberOfElements(), MockUtil.getRestaurants().size());
    }
    @Test
    void getOwnerRestaurants(){
        Mockito.when(restaurantRepository.findRestaurantByOwnerID(ArgumentMatchers.any(Long.class)))
                .thenReturn(MockUtil.getRestaurants());
        List<Restaurant> restaurants = restaurantService.getOwnerRestaurants(1L);
        assertEquals(restaurantService.getOwnerRestaurants(1L).size(), MockUtil.getRestaurants().size());

    }

    @Test
    void addRestaurantSuccessfully(){

        Mockito.when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class))).
                thenReturn(MockUtil.getRestaurant());
        Mockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class))).
                thenReturn(MockUtil.getCategory());
        Mockito.when(restaurantCategoryRepository.save(ArgumentMatchers.any(RestaurantCategory.class))).
                thenReturn(MockUtil.getRestaurantCategory());

        Mockito.when(userDetailRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(MockUtil.getUserDetail()) );
        Mockito.when(restaurantStatusRepository.findById("REGISTERED")).thenReturn(Optional.of(MockUtil.getRegisteredStatus()));
        Restaurant temp = restaurantService.addRestaurant(MockUtil.getAddRestaurantDTO());
        assertThat(temp).isNotNull();
        verify(restaurantRepository).save(any(Restaurant.class));
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
    void deleteRestaurantSuccessfully() {
        Mockito.when(restaurantRepository.findById(MockUtil.getRestaurant().getId()))
                .thenReturn(Optional.of(MockUtil.getRestaurant()));

        restaurantService.deleteRestaurant(MockUtil.getRestaurant().getId());
        verify(restaurantRepository).deleteById(MockUtil.getRestaurant().getId());

    }

    @Test
    void deleteRestaurantShouldThrowErrorWhenIDNotFound() {
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class, () -> {
            restaurantService.deleteRestaurant(MockUtil.getRestaurant().getId());
        });

    }

    @Test
    void updateRestaurantSuccessfully() {
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(MockUtil.getRestaurant()));
        Mockito.when(locationRepository.findLocationByStreet(anyString()))
                .thenReturn(null);
        Mockito.when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class))).
                thenReturn(MockUtil.getRestaurant());
        restaurantService.updateRestaurant(MockUtil.getRestaurant().getId(), MockUtil.getUpdateRestaurantDTO());
        verify(restaurantRepository).save(any(Restaurant.class));
        verify(restaurantRepository).findById(MockUtil.getRestaurant().getId());
    }

    @Test
    void updateRestaurantShouldThrowEntityNotFoundException() {
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class, () -> {
            restaurantService.updateRestaurant(1l, MockUtil.getUpdateRestaurantDTO());
        });
    }

    @Test
    void requestDeleteRestaurant(){
        //useless
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(MockUtil.getRestaurantWithActiveStatus()));
        Mockito.when(restaurantStatusRepository.findById("PENDING_DELETE")).thenReturn(Optional.of(MockUtil.getPendingDeleteStatus()));
        Mockito.when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class))).thenReturn(MockUtil.getRestaurantWithPendingDeleteStatus());
        assertEquals(restaurantService.requestDeleteRestaurant(1l).getRestaurantStatus(), MockUtil.getPendingDeleteStatus());
        verify(restaurantRepository).save(any(Restaurant.class));

    }

}

