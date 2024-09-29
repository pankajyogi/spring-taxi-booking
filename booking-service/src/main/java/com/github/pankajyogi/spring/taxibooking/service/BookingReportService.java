package com.github.pankajyogi.spring.taxibooking.service;

import com.github.pankajyogi.spring.taxibooking.model.BookingInfo;

import java.time.LocalDate;
import java.util.List;

/**
 * BookingReportService is an interface that defines methods for generating reports of bookings
 * within a specified date range.
 */
public interface BookingReportService {

    /**
     * Retrieves a list of booking information within a specified date range.
     *
     * @param startDate the start date of the range for which the bookings are to be retrieved
     * @param endDate the end date of the range for which the bookings are to be retrieved
     * @return a list of BookingInfo objects representing the bookings within the specified date range
     */
    List<BookingInfo> getBookingsBetween(LocalDate startDate, LocalDate endDate);

}
