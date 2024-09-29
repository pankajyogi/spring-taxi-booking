package com.github.pankajyogi.spring.taxibooking.web;

import com.github.pankajyogi.spring.taxibooking.Utils;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.BookingResponse;
import com.github.pankajyogi.spring.taxibooking.model.StringConstants;
import com.github.pankajyogi.spring.taxibooking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    private static final Logger logger = LoggerFactory.getLogger(BookingRestController.class);

    private final BookingService bookingService;

    @Autowired
    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping
    public BookingRequest requestBooking(
            @RequestHeader(StringConstants.CUSTOMER_ID_HEADER) Long customerId,
            @RequestBody BookingRequest bookingRequest) {
        logger.info("Received booking request from customerId: {}", customerId);
        try {
            Utils.ensureEquals(customerId, bookingRequest.getCustomerId(), "CustomerId in API token and payload does not match.");
            return bookingService.requestBooking(bookingRequest);
        } catch (Exception e) {
            logger.error("Exception during requestBooking", e);
            throw e;
        }
    }


    @PostMapping("/status")
    public BookingResponse getBookingStatus(
            @RequestHeader(StringConstants.CUSTOMER_ID_HEADER) Long customerId,
            @RequestBody BookingRequest bookingRequest) {
        logger.info("Received request to get booking status for customerId: {}, bookingId: {}", customerId, bookingRequest.getBookingId());
        try {
            Utils.ensureEquals(customerId, bookingRequest.getCustomerId(), "CustomerId in API token and payload does not match.");
            return bookingService.getBookingStatus(bookingRequest);
        } catch (Exception e) {
            logger.error("Exception during requestBooking", e);
            throw e;
        }
    }

}
