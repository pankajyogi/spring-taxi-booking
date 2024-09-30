package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.db.BookingDO;
import com.github.pankajyogi.spring.taxibooking.mapper.BookingDOMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingInfo;
import com.github.pankajyogi.spring.taxibooking.model.BookingStatus;
import com.github.pankajyogi.spring.taxibooking.service.BookingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        // Use a custom RowMapper to map BookingDO
        RowMapper<BookingDO> rowMapper = new RowMapper<BookingDO>() {
            @Override
            public BookingDO mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookingDO bookingDO = new BookingDO();
                bookingDO.setBookingId(rs.getString("booking_id"));
                bookingDO.setCustomerId(rs.getLong("customer_id"));
                bookingDO.setTaxiId(rs.getLong("taxi_id"));
                bookingDO.setPickupTime(fromTimestamp(rs.getTimestamp("pickup_time")));
                bookingDO.setDropOffTime(fromTimestamp(rs.getTimestamp("drop_off_time")));
                bookingDO.setPickupLocation(rs.getString("pickup_location"));
                bookingDO.setDropOffLocation(rs.getString("drop_off_location"));
                bookingDO.setBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));
                bookingDO.setCreatedAt(fromTimestamp(rs.getTimestamp("created_at")));
                return bookingDO;
            }
        };
        List<BookingDO> bookingDOList = jdbcTemplate.query(querySql, rowMapper);
        return bookingDOList.stream()
                .map(bookingDO -> bookingDOMapper.mapToBookingInfo(bookingDO))
                .collect(Collectors.toList());
    }

    private LocalDateTime fromTimestamp(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        else {
            return timestamp.toLocalDateTime();
        }
    }

}
