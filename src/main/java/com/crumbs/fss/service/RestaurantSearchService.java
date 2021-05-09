package com.crumbs.fss.service;

import com.crumbs.fss.ServiceUtil;
import com.crumbs.fss.entity.MenuItem;
import com.crumbs.fss.entity.Restaurant;
import com.crumbs.fss.repository.MenuItemRepository;
import com.crumbs.fss.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = { Exception.class })
public class RestaurantSearchService {
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired MenuItemRepository menuItemRepository;
    @Autowired ServiceUtil serviceUtil;

    public List<Restaurant> getRestaurants(){
        serviceUtil.makeRestaurants(); // Temporary because of in-mem DB.
        return restaurantRepository.findAll();
    }

    public List<MenuItem> getMenuItems(){
        return menuItemRepository.findAll();
    }
}
