package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.db.BookingDO;
import com.github.pankajyogi.spring.taxibooking.db.BookingRepository;
import com.github.pankajyogi.spring.taxibooking.mapper.BookingDOMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.BookingResponse;
import com.github.pankajyogi.spring.taxibooking.model.BookingStatus;
import com.github.pankajyogi.spring.taxibooking.service.BookingPublisherService;
import com.github.pankajyogi.spring.taxibooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingPublisherService bookingPublisherService;

    private final BookingRepository bookingRepository;

    private final BookingDOMapper bookingDOMapper;

    @Autowired
    public BookingServiceImpl(
            BookingPublisherService bookingPublisherService,
            BookingRepository bookingRepository,
            BookingDOMapper bookingDOMapper) {
        this.bookingPublisherService = bookingPublisherService;
        this.bookingRepository = bookingRepository;
        this.bookingDOMapper = bookingDOMapper;
    }

    @Override
    public BookingRequest requestBooking(BookingRequest bookingRequest) {
        // add validations
        BookingDO bookingDO = bookingDOMapper.mapFrom(bookingRequest);
        bookingDO.setBookingStatus(BookingStatus.REQUESTING_TAXI);
        bookingDO.setCreatedAt(LocalDateTime.now());
        bookingDO = bookingRepository.save(bookingDO);

        bookingRequest = bookingDOMapper.mapToBookingRequest(bookingDO);
        bookingPublisherService.publishBookingRequest(bookingRequest);
        return bookingRequest;
    }

    @Override
    public BookingResponse getBookingStatus(BookingRequest bookingRequest) {
        //TODO: use caching to avoid excessive DB calls
        BookingDO bookingDO = bookingRepository.findById(bookingRequest.getBookingId()).orElse(null);
        return bookingDOMapper.mapToBookingResponse(bookingDO);
    }
}
