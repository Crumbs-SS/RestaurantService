package com.crumbs.fss.controller;

import com.crumbs.fss.MockUtil;
import com.crumbs.fss.service.RestaurantSearchService;
import com.crumbs.fss.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantServiceController.class)
class RestaurantServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RestaurantService restaurantService;

    @Test
    @WithMockUser(roles = "ADMIN", username = "test")
    void getOwnerRestaurants() throws Exception{
        //verifying HTTP Request Matching + Input Serialization
        mockMvc.perform(get("/restaurant-service/owner/{username}/restaurants", "test").with(user("test").roles("ADMIN"))
                .contentType("application/json"))
                .andExpect(status().isOk());

//        //verifying input validation
//        mockMvc.perform(get("/restaurant-service/owner/{username}/restaurants", Optional.ofNullable(null))
//                .contentType("application/json"))
//                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "owner")
    void addRestaurant() throws Exception {

        //verifying HTTP Request Matching + Input Serialization
        mockMvc.perform(post("/restaurant-service/owner/{username}/restaurant", "test")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getAddRestaurantDTO())))
                .andExpect(status().isOk());

        //verifying input validation
        mockMvc.perform(post("/restaurant-service/owner/{username}/restaurant", "test")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getInvalidAddRestaurantDTO())))
                .andExpect(status().isBadRequest());

    }
    @Test
    @WithMockUser(roles = "owner")
    void updateRestaurant() throws Exception {
        //verifying HTTP Request Matching + Input Serialization
        mockMvc.perform(put("/restaurant-service/owner/{username}/restaurant/{id}", "test", MockUtil.getRestaurant().getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getUpdateRestaurantDTO())))
                .andExpect(status().isOk());

        //verifying input validation
        mockMvc.perform(put("/restaurant-service/owner/{username}/restaurant/{id}", "test", MockUtil.getRestaurant().getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(MockUtil.getInvalidAddRestaurantDTO())))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(roles = "owner")
    void requestDeleteRestaurant() throws Exception {
        mockMvc.perform(delete("/restaurant-service/owner/{username}/restaurant/{id}", "test", 1l)
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/restaurant-service/owner/{username}/restaurant/{id}", "test", Optional.of(null))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

}
