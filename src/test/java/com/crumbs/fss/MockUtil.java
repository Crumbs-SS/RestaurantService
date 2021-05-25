package com.crumbs.fss;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.DTO.updateRestaurantDTO;
import com.crumbs.fss.entity.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockUtil {

    public static List<Restaurant> getRestaurants(){
        List<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Restaurant restaurant = Restaurant.builder().name("Restaurant-" + i).build();
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    public static Page<Restaurant> getPageRestaurants(){
        return new PageImpl<>(getRestaurants());
    }

    public static Optional<Page<Restaurant>> getPageRestaurantsOptional(){
        return Optional.of(getPageRestaurants());
    }

    public static List<MenuItem> getMenuItems(){
        List<MenuItem> menuItems = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            menuItems.add(getMenuItem(i));

        return menuItems;
    }

    public static Page<MenuItem> getMenuItemsPage(){
        return new PageImpl<>(getMenuItems());
    }

    public static Optional<Page<MenuItem>> getMenuItemsPageOptional(){
        return Optional.of(getMenuItemsPage());
    }

    public static MenuItem getMenuItem(int i){
        BigDecimal bd = new BigDecimal((i+1F) * (float)Math.random() + 3)
                .setScale(2, RoundingMode.HALF_UP);
        MenuItem menuItem = MenuItem.builder()
                .name("MenuItem-"+i)
                .price(bd.floatValue())
                .description("Menu Item for a restaurant")
                .build();

        return menuItem;
    }


    public static Example<MenuItem> getMenuItemExample(){
        return Example.of(getMenuItem(1));
    }

    public static addRestaurantDTO getAddRestaurantDTO(){
        Category cat = new Category();
        List<Category> categories = new ArrayList<>();
        categories.add(cat);

        addRestaurantDTO temp = addRestaurantDTO.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .street("test")
                .city("test")
                .zip(00000)
                .state("AA")
                .name("test")
                .priceRating(1)
                .categories(null)
                .build();

        return temp;
    }
    public static updateRestaurantDTO getUpdateRestaurantDTO(){
//        Category cat = new Category();
//        List<Category> categories = new ArrayList<>();
//        categories.add(cat);

        updateRestaurantDTO temp = updateRestaurantDTO.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .street("test")
                .city("test")
                .zip(00000)
                .state("AA")
                .name("test")
                .priceRating(1)
                .categories(null)
                .build();

        return temp;
    }
    public static addRestaurantDTO getInvalidAddRestaurantDTO(){
        addRestaurantDTO temp = addRestaurantDTO.builder()
                .firstName(null)
                .lastName("test")
                .email("test@gmail.com")
                .street("test")
                .city("test")
                .zip(1111111)
                .state("AA")
                .name("test")
                .priceRating(1)
                .categories(null)
                .build();

        return temp;
    }
    public static Restaurant getRestaurant(){
        Restaurant temp = new Restaurant();
        temp.setId(1L);
        temp.setLocation(new Location());
        temp.getLocation().setId(1l);
        temp.setRestaurantOwner(new RestaurantOwner());
        temp.getRestaurantOwner().setId(1l);
        temp.getRestaurantOwner().setUserDetail(new UserDetail());
        temp.getRestaurantOwner().getUserDetail().setId(1l);
        return temp;
    }
    public static UserDetail getUserDetail() {
        return null;
    }
    public static RestaurantOwner getRestaurantOwner(){
        return null;
    }
    public static Category getCategory(){
        return null;
    }
    public static RestaurantCategory getRestaurantCategory(){
        return null;
    }
}
