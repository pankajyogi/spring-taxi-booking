package com.github.pankajyogi.spring.taxibooking.db;

import com.github.pankajyogi.spring.taxibooking.model.BookingStatus;
import com.github.pankajyogi.spring.taxibooking.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a Booking Data Object (BookingDO) mapped to the "bookings" table in the database.
 * This class is used to manage information related to a taxi booking, including customer, taxi, and driver details,
 * payment status, booking status, and the locations and times of pickup and drop-off.
 */
@Data
@Entity
@Table(name = "bookings")
public class BookingDO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    private Long customerId;

    private Long taxiId;

    private Long driverId;

    private String paymentId;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private String pickupLocation;

    private String dropOffLocation;

    private LocalDateTime pickupTime;

    private LocalDateTime dropOffTime;

    private Double fare;

    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

}
