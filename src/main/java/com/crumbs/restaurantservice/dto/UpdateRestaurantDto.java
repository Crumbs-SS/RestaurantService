package com.crumbs.restaurantservice.dto;

import com.crumbs.lib.entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRestaurantDto {

    private String firstName;
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;

    @Size(min =2, max =2, message= "State must consist of only 2 characters.")
    private String state;

    private String name;

    @Min(value = 1, message = "Price Rating is between 1 and 3")
    @Max(value = 3, message = "Price Rating is between 1 and 3")
    private Integer priceRating;

    private List<String> categories;
    private List<MenuItem> menu;
}
