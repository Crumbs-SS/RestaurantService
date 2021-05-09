package com.crumbs.fss;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUtil {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;

    public void makeRestaurants(){
        for (int i = 0; i < 10; i++) {
            Restaurant restaurant = new Restaurant("Restaurant-" + i, "1321" + i + " LN");
            makeMenuItems(restaurant);
            restaurantRepository.save(restaurant);
        }
    }

    private void makeMenuItems(Restaurant restaurant){
        for (int i = 0; i < 30; i++){
            MenuItem menuItem = new MenuItem(
                    "MenuItem-"+i,
                    i * 3.412F/i,
                    20,
                    "Menu Item for a restaurant"
            );
            menuItem.setRestaurant(restaurant);
            menuItemRepository.save(menuItem);
        }
    }
}
