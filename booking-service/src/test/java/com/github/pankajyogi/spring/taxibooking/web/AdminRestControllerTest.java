package com.github.pankajyogi.spring.taxibooking.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingInfo;
import com.github.pankajyogi.spring.taxibooking.model.StringConstants;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.service.BookingReportService;
import com.github.pankajyogi.spring.taxibooking.service.TaxiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminRestController.class)
class AdminRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaxiService taxiService;

    @MockBean
    private BookingReportService bookingReportService;

    @Test
    void getAvailableTaxis_shouldCallTaxiService() throws Exception {
        TaxiInfo taxiInfo = new TaxiInfo();
        List<TaxiInfo> taxiInfos = Collections.singletonList(taxiInfo);

        given(taxiService.getAvailableTaxis()).willReturn(taxiInfos);

        String expectedJson = objectMapper.writeValueAsString(taxiInfos);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/availableTaxis")
                        .header(StringConstants.USER_ID_HEADER, "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(taxiService).getAvailableTaxis();
        verifyNoMoreInteractions(taxiService, bookingReportService);
    }

    @Test
    void getAvailableTaxis_givenNoUserIdHeader_shouldReturnBadRequest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/availableTaxis"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBookings_shouldCallBookingReportService() throws Exception {
        BookingInfo bookingInfo = new BookingInfo();
        List<BookingInfo> bookings = Collections.singletonList(bookingInfo);

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();
        given(bookingReportService.getBookingsBetween(startDate, endDate)).willReturn(bookings);

        String expectedJson = objectMapper.writeValueAsString(bookings);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/bookingsReport")
                        .header(StringConstants.USER_ID_HEADER, "testUser")
                        .queryParam("startDate", startDate.toString())
                        .queryParam("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(bookingReportService).getBookingsBetween(startDate, endDate);
        verifyNoMoreInteractions(taxiService, bookingReportService);
    }

    @Test
    void getBookings_givenNoStartDateButHaveEndDate_shouldMinus3Months() throws Exception {
        List<BookingInfo> emptyList = Collections.emptyList();

        LocalDate endDate = LocalDate.now().minusWeeks(3);
        LocalDate startDate = endDate.minusMonths(3);
        given(this.bookingReportService.getBookingsBetween(startDate, endDate)).willReturn(emptyList);

        String expectedJson = objectMapper.writeValueAsString(emptyList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/bookingsReport")
                        .header(StringConstants.USER_ID_HEADER, "testUser")
                        .queryParam("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(bookingReportService).getBookingsBetween(startDate, endDate);
        verifyNoMoreInteractions(taxiService, bookingReportService);
    }

    @Test
    void getBookings_givenNoEndDate_shouldBeTodayDate() throws Exception {
        List<BookingInfo> emptyList = Collections.emptyList();

        LocalDate startDate = LocalDate.now().minusMonths(6);
        LocalDate endDate = LocalDate.now();

        given(this.bookingReportService.getBookingsBetween(startDate, endDate)).willReturn(emptyList);

        String expectedJson = objectMapper.writeValueAsString(emptyList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/bookingsReport")
                        .header(StringConstants.USER_ID_HEADER, "testUser")
                        .queryParam("startDate", startDate.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(bookingReportService).getBookingsBetween(startDate, endDate);
        verifyNoMoreInteractions(taxiService, bookingReportService);
    }

    @Test
    void getBookings_givenNoUserIdHeader_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/bookingsReport"))
                .andExpect(status().isBadRequest());
    }
}
