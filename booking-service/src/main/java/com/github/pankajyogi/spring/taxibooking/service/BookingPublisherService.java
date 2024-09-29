package com.github.pankajyogi.spring.taxibooking.service;

import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;

/**
 * BookingPublisherService is an interface responsible for publishing booking requests
 * to the necessary destinations. Implementations of this interface should handle the
 * logic to forward booking requests to various caches or services.
 */
public interface BookingPublisherService {

    /**
     * Publishes a booking request to the necessary destinations.
     *
     * @param bookingRequest the booking request object containing details about the booking
     */
    void publishBookingRequest(BookingRequest bookingRequest);

}
