package com.crumbs.fss;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUtil {
    @Autowired RestaurantRepository restaurantRepository;

    public void makeRestaurants(){
        for (int i = 0; i < 10; i++) {
            Restaurant restaurant = new Restaurant("Restaurant-" + i, "1321 Lane");
            restaurantRepository.save(restaurant);
        }
    }
}
