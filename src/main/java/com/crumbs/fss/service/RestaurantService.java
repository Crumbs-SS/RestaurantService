package com.crumbs.fss.service;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.DTO.updateRestaurantDTO;
import com.crumbs.fss.ExceptionHandling.DuplicateEmailException;
import com.crumbs.fss.ExceptionHandling.DuplicateLocationException;
import com.crumbs.fss.entity.*;
import com.crumbs.fss.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantOwnerRestaurants(Long id){
        return restaurantRepository.findRestaurantByOwnerID(id);
    }

    public Restaurant addRestaurant(addRestaurantDTO a) {

         if(userDetailRepository.findUserByEmail(a.getEmail())!=null)
            throw new DuplicateEmailException();

        if(locationRepository.findLocationByStreet(a.getStreet())!=null)
            throw new DuplicateLocationException();

        UserDetail userDetail = UserDetail.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .email(a.getEmail())
                .build();

        userDetailRepository.save(userDetail);

        RestaurantOwner restaurantOwner = RestaurantOwner.builder()
                .userDetail(userDetail)
                .build();

        restaurantOwnerRepository.save(restaurantOwner);

        Location location = Location.builder()
                .street(a.getStreet())
                .city(a.getCity())
                .zipCode(a.getZip())
                .state(a.getState())
                .build();

        locationRepository.save(location);

        Restaurant temp = Restaurant.builder()
                .name(a.getName())
                .priceRating(a.getPriceRating())
                .location(location)
                .restaurantOwner(restaurantOwner)
                .build();

        Restaurant restaurant = restaurantRepository.save(temp);

        List<Category> categories = a.getCategories();
        if(categories!= null && !categories.isEmpty()) {
            categories.forEach(category -> {
                restaurantCategoryRepository.insertRestaurantCategory(category.getName(), restaurant.getId());
            });
        }
        return restaurant;
    }
    public Restaurant deleteRestaurant(Long id){

        Restaurant temp = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        restaurantRepository.deleteById(id);
        locationRepository.deleteById(temp.getLocation().getId());
        if(menuItemRepository.findById(id).isPresent())
            menuItemRepository.deleteById(id);

        return temp;
    }

    public Restaurant updateRestaurant(Long id, updateRestaurantDTO updateRestaurantDTO){

        Restaurant temp = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        if(userDetailRepository.findUserByEmail(updateRestaurantDTO.getEmail())!=null)
            throw new DuplicateEmailException();

        if(locationRepository.findLocationByStreet(updateRestaurantDTO.getStreet())!=null)
            throw new DuplicateLocationException();

        // Update User Details
        String firstName = updateRestaurantDTO.getFirstName();
        if(firstName!= null && !firstName.isEmpty())
            temp.getRestaurantOwner().getUserDetail().setFirstName(firstName);

        String lastName = updateRestaurantDTO.getLastName();
        if(firstName!= null && !firstName.isEmpty())
            temp.getRestaurantOwner().getUserDetail().setLastName(lastName);

        String email = updateRestaurantDTO.getEmail();
        if(email!= null && !email.isEmpty())
            temp.getRestaurantOwner().getUserDetail().setEmail(email);

        //Update Restaurant Location
        String street = updateRestaurantDTO.getStreet();
        if(street!= null && !street.isEmpty())
            temp.getLocation().setStreet(street);

        String city = updateRestaurantDTO.getCity();
        if(city!= null && !city.isEmpty())
            temp.getLocation().setCity(city);

        Integer zip = updateRestaurantDTO.getZip();
        if(zip!= null)
            temp.getLocation().setZipCode(zip);

        String state = updateRestaurantDTO.getState();
        if(state!= null && !state.isEmpty())
            temp.getLocation().setState(state);

        //Update Restaurant Details
        String name = updateRestaurantDTO.getName();
        if(name!= null && !name.isEmpty())
            temp.setName(name);

        Integer priceRating = updateRestaurantDTO.getPriceRating();
        if(priceRating!= null)
            temp.setPriceRating(priceRating);

        //delete old categories
        if(!temp.getCategories().isEmpty())
            restaurantCategoryRepository.deleteByRestaurantID(id);

        //replace with new ones
        List<Category> newCategories = updateRestaurantDTO.getCategories();
        if(newCategories!= null && !newCategories.isEmpty()) {
            newCategories.forEach(category -> {
                restaurantCategoryRepository.insertRestaurantCategory(category.getName(),temp.getId());
            });
        }
        return restaurantRepository.save(temp);
    }


}
