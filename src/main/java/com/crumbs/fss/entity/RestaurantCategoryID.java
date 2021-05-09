package com.crumbs.fss.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RestaurantCategoryID implements Serializable {
    private long restaurantId;
    private String categoryId;


    public RestaurantCategoryID() {
    }

    public RestaurantCategoryID(long restaurantId, String categoryId) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
