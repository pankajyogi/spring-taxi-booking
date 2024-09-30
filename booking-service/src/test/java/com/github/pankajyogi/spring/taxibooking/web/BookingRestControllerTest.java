package com.github.pankajyogi.spring.taxibooking.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.BookingResponse;
import com.github.pankajyogi.spring.taxibooking.model.StringConstants;
import com.github.pankajyogi.spring.taxibooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingRestController.class)
public class BookingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @Test
    public void requestBooking_shouldCallBookingService() throws Exception {
        Long customerId = 1L;
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCustomerId(customerId);

        when(bookingService.requestBooking(any(BookingRequest.class))).thenReturn(bookingRequest);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(StringConstants.CUSTOMER_ID_HEADER, customerId)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());
        verify(bookingService).requestBooking(any(BookingRequest.class));
    }

    @Test
    void requestBooking_givenNoCustomerIdHeader_shouldReturnBadRequest() throws Exception {
        Long customerId = 1L;
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCustomerId(customerId);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest());
        verifyNoMoreInteractions(bookingService);
    }

    @Test
    public void getBookingStatus_shouldCallBookingService() throws Exception {
        Long customerId = 1L;
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCustomerId(customerId);
        bookingRequest.setBookingId(UUID.randomUUID().toString());

        BookingResponse bookingResponse = new BookingResponse();

        when(bookingService.getBookingStatus(any(BookingRequest.class))).thenReturn(bookingResponse);

        mockMvc.perform(post("/api/bookings/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(StringConstants.CUSTOMER_ID_HEADER, customerId)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());
        verify(bookingService).getBookingStatus(any(BookingRequest.class));
    }

    @Test
    void getBookingStatus_givenNoCustomerIdHeader_shouldReturnBadRequest() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCustomerId(300L);
        bookingRequest.setBookingId(UUID.randomUUID().toString());

        mockMvc.perform(post("/api/bookings/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest());
        verifyNoMoreInteractions(bookingService);
    }
}
