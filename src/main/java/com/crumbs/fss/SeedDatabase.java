package com.crumbs.fss;

import com.crumbs.fss.entity.*;
import com.crumbs.fss.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class SeedDatabase implements ApplicationRunner {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantOwnerRepository ownerRepository;
    private final UserDetailRepository userDetailRepository;
    private final LocationRepository locationRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    SeedDatabase(RestaurantRepository restaurantRepository, UserDetailRepository userDetailRepository,
                 RestaurantOwnerRepository ownerRepository, LocationRepository locationRepository,
                 MenuItemRepository menuItemRepository, RestaurantCategoryRepository restaurantCategoryRepository,
                 CategoryRepository categoryRepository){
        this.restaurantRepository = restaurantRepository;
        this.userDetailRepository = userDetailRepository;
        this.ownerRepository = ownerRepository;
        this.locationRepository = locationRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        menuItemRepository.deleteAll();
        restaurantCategoryRepository.deleteAll();
        categoryRepository.deleteAll();
        restaurantRepository.deleteAll();
        locationRepository.deleteAll();
        ownerRepository.deleteAll();
        userDetailRepository.deleteAll();


        Restaurant restaurant;
        Restaurant restaurant2;
        UserDetail userDetail;
        RestaurantOwner restaurantOwner;
        Location location;
        Location location2;
        Category cat;

        cat = Category.builder().name("American").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Burger").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Japanese").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("French").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Italian").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Pizza").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Chicken").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Burger").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Healthy").build();
        categoryRepository.save(cat);
        cat = Category.builder().name("Fine Dining").build();
        categoryRepository.save(cat);

        userDetail = UserDetail.builder()
                .firstName("Jonathan")
                .lastName("Frey")
                .email("jfrey2704@smoothstack.com")
                .build();

        userDetailRepository.save(userDetail);

        restaurantOwner = RestaurantOwner.builder()
                .userDetail(userDetail)
                .build();

        ownerRepository.save(restaurantOwner);

        location = Location.builder()
                .state("CA")
                .street("1111 Street A")
                .city("Los Angeles")
                .zipCode(12345)
                .build();

        locationRepository.save(location);

        location2 = Location.builder()
                .state("CA")
                .street("2222 Street B")
                .city("Los Angeles")
                .zipCode(12345)
                .build();

        locationRepository.save(location2);

        restaurant = Restaurant.builder()
                .restaurantOwner(restaurantOwner)
                .location(location)
                .priceRating(1)
                .rating(5)
                .name("KFC")
                .build();

        restaurant2 = Restaurant.builder()
                .id(1L)
                .restaurantOwner(restaurantOwner)
                .location(location2)
                .priceRating(2)
                .rating(3)
                .name("MCDonald's")
                .build();

        restaurant = restaurantRepository.save(restaurant);
        restaurant2 = restaurantRepository.save(restaurant2);

//        restaurantCategoryRepository.insertRestaurantCategory("Burger",restaurant.getId());
//        restaurantCategoryRepository.insertRestaurantCategory("American",restaurant.getId());
//        restaurantCategoryRepository.insertRestaurantCategory("Japanes",restaurant2.getId());
//        restaurantCategoryRepository.insertRestaurantCategory("Sushi",restaurant2.getId());

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
        for (int i = 0; i < 10; i++){
            BigDecimal bd = BigDecimal.valueOf((i + 1F) * (float) Math.random() + 3)
                    .setScale(2, RoundingMode.HALF_UP);
            Float price = bd.floatValue();

            MenuItem menuItem = MenuItem.builder()
                    .name("MenuItem-"+i)
                    .price(price)
                    .description("Menu Item for a restaurant")
                    .build();

            menuItem.setRestaurant(restaurant2);
            menuItemRepository.save(menuItem);
        }
    }


}
