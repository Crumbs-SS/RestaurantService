package com.crumbs.fss.DTO;

import com.crumbs.fss.entity.*;
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

    @NotBlank(message = "First Name is Mandatory")
    private String ownerFirstName;

    @NotBlank(message = "First Name is Mandatory")
    private String ownerLastName;

    @Email(message = "Email should be valid")
    private String ownerEmail;

    @NotBlank(message = "Street is Mandatory")
    private String street;

    @NotBlank(message = "City is Mandatory")
    private String city;

    @Digits(integer=5, fraction=0)
    private Integer zip;

    @NotBlank(message = "State is Mandatory")
    @Size(min =2, max =2, message= "State must consist of only 2 characters.")
    private String state;

    @NotBlank(message = "Restaurant Name is Mandatory")
    private String name;

    @Min(value = 1, message = "Price Rating is between 1 and 3")
    @Max(value = 3, message = "Price Rating is between 1 and 3")
    private Integer priceRating;

    private List<Category> categories;

}
