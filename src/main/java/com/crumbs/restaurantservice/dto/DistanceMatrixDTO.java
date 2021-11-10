package com.crumbs.restaurantservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistanceMatrixDTO {
    private String origin;
    private String destination;
}
