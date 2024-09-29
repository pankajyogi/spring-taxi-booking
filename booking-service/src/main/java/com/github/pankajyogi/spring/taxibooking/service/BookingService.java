package com.github.pankajyogi.spring.taxibooking.service;

import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.BookingResponse;

/**
 * BookingService is an interface responsible for handling taxi booking operations.
 * Implementers of this interface should provide mechanisms to request new bookings
 * and retrieve the status of existing bookings.
 */
public interface BookingService {
    /**
     * Requests a new booking based on the provided booking request details.
     *
     * @param bookingRequest the booking request containing relevant details such as pickup and drop-off locations, customer information, etc.
     * @return the updated booking request with additional information like booking ID, status, etc.
     */
    BookingRequest requestBooking(BookingRequest bookingRequest);

    /**
     * Retrieves the current status of an existing booking based on the provided booking request details.
     *
     * @param bookingRequest the booking request containing relevant details such as booking ID and customer ID.
     * @return the current status of the booking, including details such as OTP, taxi number, driver name, and fare.
     */
    BookingResponse getBookingStatus(BookingRequest bookingRequest);
}
