package com.crumbs.fss;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.crumbs.fss.dto.addRestaurantDto;
import com.crumbs.fss.dto.updateRestaurantDto;
import com.crumbs.lib.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static List<MenuItem> getMenuItems(){
        List<MenuItem> menuItems = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            menuItems.add(getMenuItem(i));

        return menuItems;
    }

    public static Page<MenuItem> getMenuItemsPage(){
        return new PageImpl<>(getMenuItems());
    }

    public static MenuItem getMenuItem(int i){
        BigDecimal bd = new BigDecimal((i+1F) * (float)Math.random() + 3)
                .setScale(2, RoundingMode.HALF_UP);

        return MenuItem.builder()
                .name("MenuItem-"+i)
                .price(bd.floatValue())
                .description("Menu Item for a restaurant")
                .build();
    }

    public static addRestaurantDto getAddRestaurantDTO(){
        Category cat = new Category();
        List<Category> categories = new ArrayList<>();
        categories.add(cat);

        addRestaurantDto temp = addRestaurantDto.builder()
                .street("test")
                .city("test")
                .state("AA")
                .name("test")
                .priceRating(1)
                .categories(null)
                .build();

        return temp;
    }
    public static updateRestaurantDto getUpdateRestaurantDTO(){

        updateRestaurantDto temp = updateRestaurantDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .street("test")
                .city("test")
                .state("AA")
                .name("test")
                .priceRating(1)
                .categories(null)
                .build();

        return temp;
    }
    public static updateRestaurantDto getInvalidUpdateRestaurantDTO(){
        updateRestaurantDto temp = updateRestaurantDto.builder()
                .street("test")
                .city("test")
                .state("AA")
                .name("test")
                //email is invalid
                .email("zzz")
                .priceRating(1)
                .categories(null)
                .build();

        return temp;
    }

    public static addRestaurantDto getInvalidAddRestaurantDTO(){
        addRestaurantDto temp = addRestaurantDto.builder()
                .street("test")
                .city("test")
                .state("AAA")
                .name("test")
                .priceRating(1)
                .categories(null)
                .build();

        return temp;
    }

    public static List<Category> getCategories(){
        return List.of(
                Category.builder().name("Rando").build(),
                Category.builder().name("Rando").build(),
                Category.builder().name("Rando").build());
    }

    public static Restaurant getRestaurant(){
        Restaurant temp = new Restaurant();
        temp.setId(1L);
        temp.setLocation(new Location());
        temp.getLocation().setId(1l);
        temp.setRestaurantOwner(getRestaurantOwner());
        RestaurantStatus status = new RestaurantStatus();
        status.setStatus("PENDING_DELETE");
        temp.setRestaurantStatus(status);
        return temp;
    }
    public static Restaurant getRestaurantWithActiveStatus(){
        RestaurantStatus status = new RestaurantStatus();
        status.setStatus("ACTIVE");

        Restaurant temp = new Restaurant();
        temp.setId(1L);
        temp.setLocation(new Location());
        temp.getLocation().setId(1l);
        temp.setRestaurantOwner(new Owner());
        temp.getRestaurantOwner().setId(1l);
        temp.getRestaurantOwner().setUserDetails(new UserDetails());
        temp.getRestaurantOwner().getUserDetails().setId(1l);
        temp.setRestaurantStatus(status);

        return temp;
    }
    public static Restaurant getRestaurantWithPendingDeleteStatus(){
        RestaurantStatus status = new RestaurantStatus();
        status.setStatus("PENDING_DELETE");

        Restaurant temp = new Restaurant();
        temp.setId(1L);
        temp.setLocation(new Location());
        temp.getLocation().setId(1l);
        temp.setRestaurantOwner(new Owner());
        temp.getRestaurantOwner().setId(1l);
        temp.getRestaurantOwner().setUserDetails(new UserDetails());
        temp.getRestaurantOwner().getUserDetails().setId(1l);
        temp.setRestaurantStatus(status);

        return temp;
    }
    public static UserDetails getUserDetail() {
        UserDetails user =new UserDetails();
        user.setOwner(getRestaurantOwner());
        return user;
    }
    public static RestaurantStatus getPendingDeleteStatus(){
        RestaurantStatus status = new RestaurantStatus();
        status.setStatus("PENDING_DELETE");
        return status;
    }
    public static RestaurantStatus getRegisteredStatus(){
        RestaurantStatus status = new RestaurantStatus();
        status.setStatus("REGISTERED");
        return status;
    }
    public static Owner getRestaurantOwner(){
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setRestaurants(getRestaurants());
        owner.setUserDetails(new UserDetails());
        return owner;
    }
    public  static String createMockJWT(String role){
        final long EXPIRATION_TIME = 900_000;
        String token;
        Algorithm algorithm = Algorithm.HMAC256(System.getenv("JWT_SECRET"));
        token = JWT.create()
                .withAudience("crumbs")
                .withIssuer("Crumbs")
                .withClaim("role", role)
                .withSubject("correctUsername")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);

        return token;
    }
    public static Category getCategory(){
        return null;
    }
    public static RestaurantCategory getRestaurantCategory(){
        return null;
    }
}
