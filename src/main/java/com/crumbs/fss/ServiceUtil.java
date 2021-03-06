package com.crumbs.fss;

import com.crumbs.lib.entity.Category;
import com.crumbs.lib.entity.MenuItem;
import com.crumbs.lib.entity.Restaurant;
import com.crumbs.lib.entity.RestaurantCategory;
import com.crumbs.lib.repository.CategoryRepository;
import com.crumbs.lib.repository.MenuItemRepository;
import com.crumbs.lib.repository.RestaurantCategoryRepository;
import com.crumbs.lib.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
public class ServiceUtil {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;
    @Autowired
    RestaurantCategoryRepository restaurantCategoryRepository;

    public void makeRestaurants(){
        makeCategories();
        List<Category> categories = categoryRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Restaurant restaurant = Restaurant.builder()
                    .name("Restaurant-" + i)
                    .build();

            makeMenuItems(restaurant);
            restaurantRepository.save(restaurant);

            for (int k = 0; k < 3; k++){
                int rand = new Random().nextInt(categories.size());
                RestaurantCategory restaurantCategory = new RestaurantCategory(categories.get(rand), restaurant);
                restaurantCategoryRepository.save(restaurantCategory);
            }
            restaurantRepository.save(restaurant);
        }
    }

    private void makeMenuItems(Restaurant restaurant){
        for (int i = 0; i < 10; i++){
            BigDecimal bd = BigDecimal.valueOf((i + 1F) * (float) Math.random() + 3)
                    .setScale(2, RoundingMode.HALF_UP);
            Float price = bd.floatValue();

            MenuItem menuItem = MenuItem.builder()
                    .name("MenuItem-"+i)
                    .price(price)
                    .description("Menu Item for a restaurant")
                    .build();

            menuItem.setRestaurant(restaurant);
            menuItemRepository.save(menuItem);
        }
    }
    
    private void makeCategories(){
        Category burger = Category.builder().name("burger").build();
        Category wings = Category.builder().name("wings").build();
        Category chicken = Category.builder().name("chicken").build();
        Category chinese = Category.builder().name("chinese").build();
        Category seafood = Category.builder().name("seafood").build();
        Category vegan = Category.builder().name("vegan").build();

        categoryRepository.save(burger);
        categoryRepository.save(wings);
        categoryRepository.save(chicken);
        categoryRepository.save(chinese);
        categoryRepository.save(seafood);
        categoryRepository.save(vegan);
    }
}
