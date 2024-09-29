package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.db.BookingDO;
import com.github.pankajyogi.spring.taxibooking.mapper.BookingDOMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingInfo;
import com.github.pankajyogi.spring.taxibooking.service.BookingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingReportServiceImpl implements BookingReportService {

    private final JdbcTemplate jdbcTemplate;

    private final BookingDOMapper bookingDOMapper;

    @Autowired
    public BookingReportServiceImpl(JdbcTemplate jdbcTemplate, BookingDOMapper bookingDOMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookingDOMapper = bookingDOMapper;
    }

    @Override
    public List<BookingInfo> getBookingsBetween(LocalDate startDate, LocalDate endDate) {
        String querySql = String.format(
                "SELECT * FROM bookings WHERE created_at BETWEEN '%s' AND '%s'",
                startDate, endDate);
        List<BookingDO> bookingDOList = jdbcTemplate.queryForList(querySql, BookingDO.class);
        return bookingDOList.stream()
                .map(bookingDO -> bookingDOMapper.mapToBookingInfo(bookingDO))
                .collect(Collectors.toList());
    }
}
