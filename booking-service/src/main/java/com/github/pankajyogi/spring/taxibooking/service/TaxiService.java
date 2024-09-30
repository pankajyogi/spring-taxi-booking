package com.github.pankajyogi.spring.taxibooking.service;


import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;

import java.util.List;

/**
 * TaxiService is an interface that defines methods to manage the status of taxis and retrieve available taxis.
 */
public interface TaxiService {

    /**
     * Retrieves a list of taxis that are currently available.
     *
     * @return a list of TaxiInfo objects representing the available taxis.
     */
    List<TaxiInfo> getAvailableTaxis();

    /**
     * Updates the status of a taxi with the provided taxi information.
     *
     * @param taxiInfo the information of the taxi whose status is to be updated, including taxi ID, current status, and location.
     */
    void updateTaxiStatus(TaxiInfo taxiInfo);


    /**
     * Retrieves the booking request associated with the specified taxi.
     *
     * @param taxiId the identifier of the taxi for which the booking request is to be retrieved
     * @return the booking request associated with the given taxiId, or null if no booking request is found
     */
    BookingRequest getBookingRequest(Long taxiId);


    /**
     * Accepts a booking request and processes it to generate a booking response.
     *
     * @param bookingRequest the booking request to be processed
     * @return a BookingResponse object containing details of the accepted booking
     */
    BookingRequest acceptBookingRequest(BookingRequest bookingRequest);
}
