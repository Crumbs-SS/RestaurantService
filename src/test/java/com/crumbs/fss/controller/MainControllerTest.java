package com.crumbs.fss.controller;

import com.crumbs.fss.DTO.addRestaurantDTO;
import com.crumbs.fss.MockUtil;
import com.crumbs.fss.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RestaurantService restaurantService;

    @Test
    void getRestaurants() throws Exception {
        Mockito.when(restaurantService.getRestaurants())
                .thenReturn(MockUtil.getRestaurants());

        mockMvc.perform(get("/restaurants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(MockUtil.getRestaurants().size())));
    }

    @Test
    void getMenuItems() throws Exception {
        Mockito.when(restaurantService.getMenuItems())
                .thenReturn(MockUtil.getMenuItems());

        mockMvc.perform(get("/menuitems")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(MockUtil.getMenuItems().size())));
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

//        //verifying output serialization
//        MvcResult mvcResult = mockMvc.perform(post("/restaurants")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(MockUtil.getAddRestaurantDTO())))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        addRestaurantDTO expectedResponseBody = MockUtil.getAddRestaurantDTO();
//        String actualResponseBody = mvcResult.getResponse().getContentAsString();
//        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
//                objectMapper.writeValueAsString(expectedResponseBody));



    }
}