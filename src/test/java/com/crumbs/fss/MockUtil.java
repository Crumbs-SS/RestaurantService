package com.crumbs.fss;

import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
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
                .quantity(20)
                .description("Menu Item for a restaurant")
                .build();

        return menuItem;
    }

    public static Example<MenuItem> getMenuItemExample(){
        return Example.of(getMenuItem(1));
    }

}
