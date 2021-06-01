package com.crumbs.fss.repository;

import com.crumbs.fss.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select r from restaurant r where restaurant_owner_id = ?1")
    List<Restaurant> findRestaurantByOwnerID(Long id);
}
