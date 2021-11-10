package com.crumbs.restaurantservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DistanceInformation {
    private BigDecimal customerLat;
    private BigDecimal customerLng;
    private Integer maxDistance;
}
