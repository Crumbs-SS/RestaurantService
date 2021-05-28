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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

//        PageRequest pageRequest = PageRequest.of(0, 5);
//
//        Mockito.when(restaurantSearchService.getPageRequest(0, 5)).thenReturn(pageRequest);
//        Mockito.when(restaurantSearchService.getRestaurants(pageRequest))
//                .thenReturn(MockUtil.getPageRestaurantsOptional());
//
//        mockMvc.perform(get("/restaurants")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }

    @Test
    void getMenuItems() throws Exception {
//        PageRequest pageRequest = PageRequest.of(0, 20);
//
//        Mockito.when(restaurantSearchService.getPageRequest(0, 10)).thenReturn(pageRequest);
//        Mockito.when(restaurantSearchService.getMenuItems(pageRequest))
//                .thenReturn(MockUtil.getMenuItemsPageOptional());
//
//        mockMvc.perform(get("/menuitems")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
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

}