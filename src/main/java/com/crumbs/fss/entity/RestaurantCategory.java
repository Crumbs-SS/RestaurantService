package com.crumbs.fss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "restaurant_category")
public class RestaurantCategory {

    @EmbeddedId
    private RestaurantCategoryID id;

    @MapsId("categoryId")
    @ManyToOne
    private Category category;

    @MapsId("restaurantId")
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    public RestaurantCategory(Category category, Restaurant restaurant) {
        this.category = category;
        this.restaurant = restaurant;
        this.id = new RestaurantCategoryID(restaurant.getId(),
                category.getName());
    }
}
