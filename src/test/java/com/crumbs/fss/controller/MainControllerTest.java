package com.crumbs.fss.controller;

import com.crumbs.fss.MockUtil;
import com.crumbs.fss.service.RestaurantSearchService;
import com.crumbs.fss.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RestaurantSearchService restaurantSearchService;

    @MockBean
    RestaurantService restaurantService;

    @Test
    void getRestaurants() throws Exception {
        mockMvc.perform(get("/restaurants")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getMenuItems() throws Exception {
        mockMvc.perform(get("/menuitems")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    @Test
    void getRestaurantss() throws Exception {

        mockMvc.perform(get("/restaurantss")
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void getRestaurantOwnerRestaurants() throws Exception{

        mockMvc.perform(get("/restaurants/{id}", MockUtil.getRestaurant().getId()))
                .andExpect(status().isOk());
    }


    @Test
    void addRestaurant() throws Exception {

        //verifying HTTP Request Matching + Input Serialization
        mockMvc.perform(post("/restaurants")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getAddRestaurantDTO())))
                .andExpect(status().isOk());

        //verifying input validation
        mockMvc.perform(post("/restaurants")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getInvalidAddRestaurantDTO())))
                .andExpect(status().isBadRequest());

    }
    @Test
    void updateRestaurant() throws Exception{
        //verifying HTTP Request Matching + Input Serialization
        mockMvc.perform(put("/restaurants/{id}", MockUtil.getRestaurant().getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getUpdateRestaurantDTO())))
                .andExpect(status().isOk());

        //verifying input validation
        mockMvc.perform(put("/restaurants/{id}", MockUtil.getRestaurant().getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getInvalidAddRestaurantDTO())))
                .andExpect(status().isBadRequest());

    }


    @Test
    void deleteRestaurant() throws Exception{
        mockMvc.perform(delete("/restaurants/{id}", MockUtil.getRestaurant().getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/restaurants/{id}", Optional.ofNullable(null))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

}
