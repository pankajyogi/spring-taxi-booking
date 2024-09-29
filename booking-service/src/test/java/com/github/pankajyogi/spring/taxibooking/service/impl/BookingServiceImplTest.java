package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.db.BookingDO;
import com.github.pankajyogi.spring.taxibooking.db.BookingRepository;
import com.github.pankajyogi.spring.taxibooking.mapper.BookingDOMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.BookingResponse;
import com.github.pankajyogi.spring.taxibooking.model.BookingStatus;
import com.github.pankajyogi.spring.taxibooking.service.BookingPublisherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingPublisherService bookingPublisherService;

    @Mock
    private BookingRepository bookingRepository;

    @Spy
    private BookingDOMapper bookingDOMapper = Mappers.getMapper(BookingDOMapper.class);

    @Test
    public void requestBooking_shouldCallDownstreamServices() {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCustomerId(100L);
        bookingRequest.setBookingStatus(BookingStatus.REQUESTED_BY_CUSTOMER);

        BookingDO bookingDO = bookingDOMapper.mapFrom(bookingRequest);
        bookingDO.setBookingStatus(BookingStatus.REQUESTING_TAXI);

        when(bookingRepository.save(any(BookingDO.class))).thenReturn(bookingDO);

        BookingRequest updatedBookingRequest = bookingService.requestBooking(bookingRequest);

        assertEquals(updatedBookingRequest.getBookingStatus(), BookingStatus.REQUESTING_TAXI);
        assertEquals(updatedBookingRequest.getCustomerId(), 100L);
        verify(bookingRepository).save(any(BookingDO.class));
        verify(bookingPublisherService).publishBookingRequest(any(BookingRequest.class));
    }

    @Test
    public void getBookingStatus_givenValidBooking_shouldReturnStatus() {
        BookingDO bookingDO = new BookingDO();
        bookingDO.setBookingId(UUID.randomUUID().toString());
        bookingDO.setBookingStatus(BookingStatus.TAXI_ALLOCATED);
        bookingDO.setTaxiId(50L);
        bookingDO.setCustomerId(200L);

        BookingRequest bookingRequest = bookingDOMapper.mapToBookingRequest(bookingDO);

        when(bookingRepository.findById(any(String.class))).thenReturn(Optional.of(bookingDO));

        BookingResponse bookingResponse = bookingService.getBookingStatus(bookingRequest);

        assertEquals(bookingResponse.getBookingStatus(), BookingStatus.TAXI_ALLOCATED);
        assertEquals(bookingResponse.getTaxiId(), 50L);
        assertEquals(bookingResponse.getCustomerId(), 200L);
        assertEquals(bookingResponse.getBookingId(), bookingDO.getBookingId());
        verify(bookingRepository).findById(any(String.class));
        verify(bookingDOMapper).mapToBookingResponse(any(BookingDO.class));
    }

    @Test
    public void getBookingStatusTest_givenInvalidBooking_shouldReturnNull() {
        BookingDO bookingDO = new BookingDO();
        bookingDO.setBookingId(UUID.randomUUID().toString());
        BookingRequest bookingRequest = bookingDOMapper.mapToBookingRequest(bookingDO);

        when(bookingRepository.findById(any(String.class))).thenReturn(Optional.empty());

        BookingResponse bookingResponse = bookingService.getBookingStatus(bookingRequest);

        assertNull(bookingResponse);
        verify(bookingRepository).findById(any(String.class));
    }
}
