package com.github.pankajyogi.spring.taxibooking.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingInfo {

    private String bookingId;

    private Long customerId;

    private Long taxiId;

    private Long driverId;

    private String paymentId;

    private BookingStatus bookingStatus;

    private LocationInfo pickupLocation;

    private LocationInfo dropOffLocation;

    private LocalDateTime pickupTime;

    private LocalDateTime dropOffTime;

    private Double fare;

    private PaymentStatus paymentStatus;
}
