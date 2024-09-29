package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.db.BookingDO;
import com.github.pankajyogi.spring.taxibooking.mapper.BookingDOMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookingReportServiceImplTest {

    @InjectMocks
    private BookingReportServiceImpl bookingReportServiceImpl;

    @Spy
    private BookingDOMapper bookingDOMapper = Mappers.getMapper(BookingDOMapper.class);

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getBookingsBetween_shouldReturnBookings() {
        BookingDO bookingDO = new BookingDO();
        BookingInfo bookingInfo = new BookingInfo();

        when(jdbcTemplate.queryForList(any(String.class), any(Class.class))).thenReturn(List.of(bookingDO));

        List<BookingInfo> result = bookingReportServiceImpl.getBookingsBetween(LocalDate.now().minusMonths(1), LocalDate.now());

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isEqualTo(bookingInfo);
    }

    @Test
    public void getBookingsBetween_shouldReturnEmptyListIfNoBookings() {
        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now().minusDays(1);

        when(jdbcTemplate.queryForList(any(String.class), any(Class.class))).thenReturn(Collections.emptyList());

        List<BookingInfo> result = bookingReportServiceImpl.getBookingsBetween(startDate, endDate);

        assertTrue(result.isEmpty());
    }

}