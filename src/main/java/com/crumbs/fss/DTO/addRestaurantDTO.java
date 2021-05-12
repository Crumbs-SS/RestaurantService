package com.crumbs.fss.DTO;

import com.crumbs.fss.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class addRestaurantDTO {

    private String ownerFirstName;
    private String ownerLastName;
    private String ownerEmail;

    private String street;
    private String city;
    private String zip;
    private String state;

    private String name;
    private Integer priceRating;
    private List<Category> categories;
    private List<MenuItem> menuItems;

//    private RestaurantOwner restaurantOwner;
//    private Location location;
//    private List<RestaurantCategory> categories;
//    private List<MenuItem> menuItems;

}
