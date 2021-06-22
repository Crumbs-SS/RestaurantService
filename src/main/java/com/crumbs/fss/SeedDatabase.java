package com.crumbs.fss;


import com.crumbs.lib.entity.*;
import com.crumbs.lib.repository.*;
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
    private final UserDetailsRepository userDetailRepository;
    private final LocationRepository locationRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserStatusRepository userStatusRepository;
    private final RestaurantStatusRepository restaurantStatusRepository;

    @Autowired
    SeedDatabase(RestaurantRepository restaurantRepository, UserDetailsRepository userDetailRepository,
                 RestaurantOwnerRepository ownerRepository, LocationRepository locationRepository,
                 MenuItemRepository menuItemRepository, RestaurantCategoryRepository restaurantCategoryRepository,
                 CategoryRepository categoryRepository, UserStatusRepository userStatusRepository, RestaurantStatusRepository restaurantStatusRepository){
        this.restaurantRepository = restaurantRepository;
        this.userDetailRepository = userDetailRepository;
        this.ownerRepository = ownerRepository;
        this.locationRepository = locationRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.userStatusRepository = userStatusRepository;
        this.restaurantStatusRepository = restaurantStatusRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        menuItemRepository.deleteAll();
        restaurantCategoryRepository.deleteAll();
        categoryRepository.deleteAll();
        restaurantRepository.deleteAll();
        locationRepository.deleteAll();
        ownerRepository.deleteAll();
        userDetailRepository.deleteAll();


        Restaurant restaurant;
        Restaurant restaurant2;
        UserDetails userDetail;
        Owner restaurantOwner;
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

        UserStatus status = new UserStatus();
        status.setStatus("active");
        userStatusRepository.save(status);

        userDetail = UserDetails.builder()
                .firstName("Jonathan")
                .lastName("Frey")
                .email("jfrey2704@smoothstack.com")
                .password(password)
                .username("a")
                .phone("1111111111")
                .build();


        restaurantOwner = Owner.builder()
                .userDetails(userDetail)
                .userStatus(status)
                .build();

        userDetail.setOwner(restaurantOwner);
        userDetailRepository.save(userDetail);
//      ownerRepository.save(restaurantOwner);

        location = Location.builder()
                .state("CA")
                .street("1111 Street A")
                .city("Los Angeles")
                .zipCode("12345")
                .build();

        locationRepository.save(location);

        location2 = Location.builder()
                .state("CA")
                .street("2222 Street B")
                .city("Los Angeles")
                .zipCode("12345")
                .build();

        locationRepository.save(location2);

        RestaurantStatus resStatus = new RestaurantStatus();
        resStatus.setStatus("active");
        restaurantStatusRepository.save(resStatus);

        restaurant = Restaurant.builder()
                .restaurantOwner(restaurantOwner)
                .location(location)
                .priceRating(1)
                .rating(5)
                .name("KFC")
                .restaurantStatus(resStatus)
                .build();

        restaurant2 = Restaurant.builder()
                .restaurantOwner(restaurantOwner)
                .location(location2)
                .priceRating(2)
                .rating(3)
                .name("MCDonald's")
                .restaurantStatus(resStatus)
                .build();

        restaurant = restaurantRepository.save(restaurant);
        restaurant2 = restaurantRepository.save(restaurant2);

//        restaurantCategoryRepository.insertRestaurantCategory("Burger",restaurant.getId());
//        restaurantCategoryRepository.insertRestaurantCategory("American",restaurant.getId());
//        restaurantCategoryRepository.insertRestaurantCategory("Japanes",restaurant2.getId());
//        restaurantCategoryRepository.insertRestaurantCategory("Sushi",restaurant2.getId());

        for (int i = 0; i < 5; i++){
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
        for (int i = 0; i < 5; i++){
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
