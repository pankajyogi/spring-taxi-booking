package com.github.pankajyogi.spring.taxibooking.model;

public enum BookingStatus {

    REQUESTED_BY_CUSTOMER,

    REQUESTING_TAXI,

    TAXI_ALLOCATED,

    TRIP_IN_PROGRESS,

    TRIP_COMPLETED,

    TERMINATED;
}
