package com.crumbs.fss.controller;

import com.crumbs.fss.MockUtil;
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
@WebMvcTest(RestaurantServiceController.class)
class RestaurantServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RestaurantService restaurantService;
//
//    @Test
//    void getOwnerRestaurants() throws Exception{
//        //verifying HTTP Request Matching + Input Serialization
//        mockMvc.perform(get("/restaurant-service/owner/{username}/restaurants", "correctUsername")
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
//                .contentType("application/json"))
//                .andExpect(status().isOk());
//
//        //verifying authorization with username
//        // why is it returning 500 instead of 401? good question
//        mockMvc.perform(get("/restaurant-service/owner/{username}/restaurants", "wrongUsername")
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
//                .contentType("application/json"))
//                .andExpect(status().isInternalServerError());
//    }
//
//    @Test
//    void addRestaurant() throws Exception {
//
//        //verifying HTTP Request Matching + Input Serialization
//        mockMvc.perform(post("/restaurant-service/owner/{username}/restaurant", "correctUsername")
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(MockUtil.getAddRestaurantDTO())))
//                .andExpect(status().isOk());
//
//        //verifying input validation
//        mockMvc.perform(post("/restaurant-service/owner/{username}/restaurant", "correctUsername")
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(MockUtil.getInvalidAddRestaurantDTO())))
//                .andExpect(status().isBadRequest());
//
//        //verifying authorization with role
//        mockMvc.perform(post("/restaurant-service/owner/{username}/restaurant", "correctUsername")
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("wrongRole")))
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(MockUtil.getAddRestaurantDTO())))
//                .andExpect(status().isInternalServerError());
//    }
//    @Test
//    void updateRestaurant() throws Exception {
//        //verifying HTTP Request Matching + Input Serialization
//        mockMvc.perform(put("/restaurant-service/owner/{username}/restaurant/{id}", "correctUsername", 1l)
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(MockUtil.getUpdateRestaurantDTO())))
//                .andExpect(status().isOk());
//
//        //verifying input validation
//        mockMvc.perform(put("/restaurant-service/owner/{username}/restaurant/{id}", "correctUsername", 1l)
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(MockUtil.getInvalidAddRestaurantDTO())))
//                .andExpect(status().isBadRequest());
//
//        //verifying authorization with role
//        mockMvc.perform(post("/restaurant-service/owner/{username}/restaurant", "correctUsername")
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("wrongRole")))
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(MockUtil.getAddRestaurantDTO())))
//                .andExpect(status().isInternalServerError());
//
//    }
//
//    @Test
//    void requestDeleteRestaurant() throws Exception {
//        mockMvc.perform(delete("/restaurant-service/owner/{username}/restaurant/{id}", "correctUsername", 1l)
//                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
//                .contentType("application/json"))
//                .andExpect(status().isOk());
//
////        mockMvc.perform(delete("/restaurant-service/owner/{username}/restaurant/{id}", "correctUsername", Optional.of(null))
////                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("OWNER")))
////                .contentType("application/json"))
////                .andExpect(status().isBadRequest());
//    }

}
