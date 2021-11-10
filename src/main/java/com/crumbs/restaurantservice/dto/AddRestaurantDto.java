package com.crumbs.restaurantservice.dto;


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
public class AddRestaurantDto {
    @NotBlank(message = "Address is mandatory")
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;

    @NotBlank(message = "Restaurant Name is Mandatory")
    private String name;

    @Min(value = 1, message = "Price Rating is between 1 and 3")
    @Max(value = 3, message = "Price Rating is between 1 and 3")
    private Integer priceRating;

    private List<String> categories;
}
