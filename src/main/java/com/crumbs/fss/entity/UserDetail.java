package com.crumbs.fss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_detail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "userDetail", cascade = CascadeType.ALL)
    private List<RestaurantOwner> restaurantOwners;
}
