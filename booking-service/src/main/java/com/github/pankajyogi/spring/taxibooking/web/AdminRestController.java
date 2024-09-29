package com.github.pankajyogi.spring.taxibooking.web;

import com.github.pankajyogi.spring.taxibooking.model.BookingInfo;
import com.github.pankajyogi.spring.taxibooking.model.StringConstants;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.service.BookingReportService;
import com.github.pankajyogi.spring.taxibooking.service.TaxiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    public static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    private final TaxiService taxiService;

    private final BookingReportService bookingReportService;

    @Autowired
    public AdminRestController(TaxiService taxiService, BookingReportService bookingReportService) {
        this.taxiService = taxiService;
        this.bookingReportService = bookingReportService;
    }

    @GetMapping("/availableTaxis")
    public List<TaxiInfo> getAvailableTaxis(@RequestHeader(StringConstants.USER_ID_HEADER) String user) {
        logger.info("Received request to fetch all available taxis by user: {}", user);
        return taxiService.getAvailableTaxis();
    }

    @GetMapping("/bookingsReport")
    public List<BookingInfo> getBookings(
            @RequestHeader(StringConstants.USER_ID_HEADER) String user,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        if (startDate == null) {
            startDate = endDate.minusMonths(3);
        }
        logger.info("Received request to fetch all bookings between startDate: {} and endDate: {} by user: {}", startDate, endDate, user);
        return bookingReportService.getBookingsBetween(startDate, endDate);
    }

}
