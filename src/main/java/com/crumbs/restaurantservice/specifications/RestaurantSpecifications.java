package com.crumbs.restaurantservice.specifications;

import com.crumbs.lib.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecifications {
    private RestaurantSpecifications(){
        throw new IllegalStateException("Utility Class");
    }

    private static Specification<Restaurant> getRestaurantsByName(String name){
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Restaurant> getRestaurantsByQuery(String query) {
        return getRestaurantsByName(query);
    }
}
