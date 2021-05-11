package com.crumbs.fss.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "restaurant_owner")
public class RestaurantOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserDetail userDetail;

    @OneToMany(mappedBy = "restaurantOwner")
    @JsonIgnoreProperties("restaurantOwner")
    private List<Restaurant> restaurants = new ArrayList<>();
}
