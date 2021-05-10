package com.crumbs.fss.repository;

import com.crumbs.fss.entity.RestaurantCategory;
import com.crumbs.fss.entity.RestaurantCategoryID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, RestaurantCategoryID> {
}
