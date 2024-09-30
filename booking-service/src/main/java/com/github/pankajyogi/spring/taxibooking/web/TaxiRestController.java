package com.github.pankajyogi.spring.taxibooking.web;

import com.github.pankajyogi.spring.taxibooking.Utils;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.StringConstants;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.service.TaxiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/taxis")
public class TaxiRestController {

    public static final Logger logger = LoggerFactory.getLogger(TaxiRestController.class);

    private final TaxiService taxiService;

    @Autowired
    public TaxiRestController(final TaxiService taxiService) {
        this.taxiService = taxiService;
    }


    @PutMapping("/updateStatus")
    public void updateTaxiStatus(
            @RequestHeader(StringConstants.TAXI_ID_HEADER) Long taxiId,
            @RequestBody TaxiInfo taxiInfo) {
        logger.info("Received request to update taxi status for taxiId: {}", taxiId);
        try {
            Utils.ensureEquals(taxiId, taxiInfo.getTaxiId(), "TaxiId in API token and payload does not match.");
            taxiService.updateTaxiStatus(taxiInfo);
        } catch (Exception e) {
            logger.error("Exception during updateTaxiStatus", e);
            throw e;
        }
    }


    @GetMapping("/bookingRequest")
    public ResponseEntity<BookingRequest> getBookingRequest(@RequestHeader(StringConstants.TAXI_ID_HEADER) Long taxiId) {
        logger.info("Received request to get booking request for taxiId: {}", taxiId);
        BookingRequest bookingRequest = taxiService.getBookingRequest(taxiId);
        if (bookingRequest == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bookingRequest);
    }


    @PostMapping("/acceptBooking")
    public BookingRequest acceptBooking(
            @RequestHeader(StringConstants.TAXI_ID_HEADER) Long taxiId,
            @RequestBody BookingRequest bookingRequest) {
        logger.info("Received request to accept booking for taxiId: {}, bookingId: {}", taxiId, bookingRequest.getBookingId());
        try {
            Utils.ensureEquals(taxiId, bookingRequest.getTaxiId(), "TaxiId in API token and payload does not match.");
            return taxiService.acceptBookingRequest(bookingRequest);
        } catch (Exception e) {
            logger.error("Exception during acceptBooking", e);
            throw e;
        }
    }
}
