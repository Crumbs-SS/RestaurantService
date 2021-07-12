package com.crumbs.fss.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class addRestaurantDTO {

    @NotNull(message = "owner_id is mandatory")
    private Long ownerId;

    @NotBlank(message = "Street is Mandatory")
    private String street;

    @NotBlank(message = "City is Mandatory")
    private String city;

    @NotBlank(message = "Zip code is Mandatory")
    @Size(min=5, max=5, message="zip must be of length 5")
    private String zip;

    @NotBlank(message = "State is Mandatory")
    @Size(min =2, max =2, message= "State must consist of only 2 characters.")
    private String state;

    @NotBlank(message = "Restaurant Name is Mandatory")
    private String name;

    @Min(value = 1, message = "Price Rating is between 1 and 3")
    @Max(value = 3, message = "Price Rating is between 1 and 3")
    private Integer priceRating;

    private List<String> categories;

}
