package com.crumbs.restaurantservice.controller;

import com.crumbs.restaurantservice.MockUtil;
import com.crumbs.restaurantservice.service.RestaurantSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantSearchServiceController.class)
class RestaurantSearchServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RestaurantSearchService restaurantSearchService;

    @Test
    @WithMockUser()
    void getRestaurant() throws Exception{

        //verifying HTTP Request Matching + Input Serialization
        mockMvc.perform(get("/restaurant-service/restaurants/{restaurantId}", 1L)
                .contentType("application/json"))
                .andExpect(status().isOk());

        //verifying input validation
        mockMvc.perform(get("/restaurant-service/restaurants/{restaurantId}",Optional.ofNullable(null))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser()
    void getRestaurants() throws Exception {
        mockMvc.perform(get("/restaurant-service/restaurants")
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/restaurant-service/restaurants/search")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    void getMenuItems() throws Exception {
        mockMvc.perform(get("/restaurant-service/restaurants/{restaurantId}/menuitems", MockUtil.getRestaurant().getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/restaurant-service/restaurants/menuitems")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    void getCategories() throws Exception {
        mockMvc.perform(get("/restaurant-service/categories")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
