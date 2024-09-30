package com.github.pankajyogi.spring.taxibooking.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.StringConstants;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.service.TaxiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaxiRestController.class)
public class TaxiRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaxiService taxiService;

    @Test
    void updateTaxiStatus_shouldCallTaxiService() throws Exception {
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTaxiId(1L);

        mockMvc.perform(put("/api/taxis/updateStatus")
                        .header(StringConstants.TAXI_ID_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taxiInfo)))
                .andExpect(status().isOk());
        verify(taxiService).updateTaxiStatus(any(TaxiInfo.class));
    }

    @Test
    void updateTaxiStatus_givenNoTaxiIdHeader_shouldReturnBadRequest() throws Exception {
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTaxiId(1L);

        mockMvc.perform(put("/api/taxis/updateStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taxiInfo)))
                .andExpect(status().isBadRequest());
        verify(taxiService, times(0)).updateTaxiStatus(any(TaxiInfo.class));
    }

    @Test
    void getBookingRequest_shouldCallTaxiService() throws Exception {

        BookingRequest bookingRequest = new BookingRequest();
        given(taxiService.getBookingRequest(anyLong())).willReturn(bookingRequest);

        mockMvc.perform(get("/api/taxis/bookingRequest")
                        .header(StringConstants.TAXI_ID_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(taxiService).getBookingRequest(1L);
    }

    @Test
    void getBookingRequest_givenNoTaxiIdHeader_shouldReturnBadRequest() throws Exception {
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTaxiId(1L);

        mockMvc.perform(get("/api/taxis/bookingRequest"))
                .andExpect(status().isBadRequest());
        verify(taxiService, times(0)).getBookingRequest(anyLong());
    }

    @Test
    void getBookingRequest_givenNoBookingRequestExist_shouldReturnNoContent() throws Exception {
        given(taxiService.getBookingRequest(anyLong())).willReturn(null);

        mockMvc.perform(get("/api/taxis/bookingRequest")
                        .header(StringConstants.TAXI_ID_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(taxiService).getBookingRequest(1L);
    }

    @Test
    void acceptBooking_shouldCallTaxiService() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setTaxiId(1L);
        BookingRequest expectedResponse = new BookingRequest();

        given(taxiService.acceptBookingRequest(any(BookingRequest.class))).willReturn(expectedResponse);

        mockMvc.perform(post("/api/taxis/acceptBooking")
                        .header(StringConstants.TAXI_ID_HEADER, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());
        verify(taxiService).acceptBookingRequest(any(BookingRequest.class));
    }

    @Test
    void acceptBooking_givenNoTaxiIdHeader_shouldReturnBadRequest() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setTaxiId(1L);

        mockMvc.perform(post("/api/taxis/acceptBooking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest());
        verify(taxiService, times(0)).acceptBookingRequest(any(BookingRequest.class));
    }
}
