package com.github.pankajyogi.spring.taxibooking.model;

import lombok.Data;

@Data
public class TaxiInfo {

    private Long taxiId;

    private TaxiStatus taxiStatus;

    private LocationInfo currentLocation;
}
