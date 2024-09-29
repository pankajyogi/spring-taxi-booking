package com.github.pankajyogi.spring.taxibooking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookingResponse extends BookingInfo {

    private String otp;

    private String taxiNumber;

    private String driverName;

    private Double fare;

}
