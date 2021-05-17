package com.crumbs.fss.repository;

import com.crumbs.fss.entity.Category;
import com.crumbs.fss.entity.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {
}
