package com.crumbs.fss.repository;

import com.crumbs.fss.entity.RestaurantCategory;
import com.crumbs.fss.entity.RestaurantCategoryID;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface
RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, RestaurantCategoryID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM restaurant_category WHERE restaurant_id= ?1")
    void deleteByRestaurantID(Long id);

    @Modifying
    @Query(value = "INSERT INTO restaurant_category VALUES (:categoryName, :restaurantID)", nativeQuery = true)
    void insertRestaurantCategory(@Param("categoryName") String categoryName, @Param("restaurantID") Long restaurantID);
}
