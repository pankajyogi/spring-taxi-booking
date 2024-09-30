package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.db.BookingDO;
import com.github.pankajyogi.spring.taxibooking.db.BookingRepository;
import com.github.pankajyogi.spring.taxibooking.exception.InvalidStateException;
import com.github.pankajyogi.spring.taxibooking.internal.cache.Cache;
import com.github.pankajyogi.spring.taxibooking.internal.cache.CacheManager;
import com.github.pankajyogi.spring.taxibooking.mapper.BookingDOMapper;
import com.github.pankajyogi.spring.taxibooking.model.*;
import com.github.pankajyogi.spring.taxibooking.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaxiServiceImpl implements TaxiService {

    private final BookingRepository bookingRepository;
    private final CacheManager cacheManager;
    private final Cache<Long, TaxiInfo> availableTaxisCache;
    private final BookingDOMapper bookingDOMapper;

    @Autowired
    public TaxiServiceImpl(BookingRepository bookingRepository, BookingDOMapper bookingDOMapper,
                           CacheManager cacheManager) {
        this.bookingRepository = bookingRepository;
        this.bookingDOMapper = bookingDOMapper;
        this.cacheManager = cacheManager;
        this.availableTaxisCache = cacheManager.getCache("AvailableTaxisCache");
    }

    @Override
    public List<TaxiInfo> getAvailableTaxis() {
        return availableTaxisCache.values();
    }

    @Override
    public void updateTaxiStatus(TaxiInfo taxiInfo) {
        if (TaxiStatus.AVAILABLE == taxiInfo.getTaxiStatus()) {
            availableTaxisCache.put(taxiInfo.getTaxiId(), taxiInfo);
        } else {
            availableTaxisCache.remove(taxiInfo.getTaxiId());
        }
    }

    @Override
    public BookingRequest getBookingRequest(Long taxiId) {
        Cache<Long, BookingRequest> taxiBookingCache = cacheManager.getCache(taxiId + "-bookings");
        // retrieve first entry from cache
        List<BookingRequest> bookingRequestList = taxiBookingCache.values();
        if (bookingRequestList.isEmpty()) {
            return null;
        }
        // continue fetching booking if available
        BookingRequest bookingRequest = bookingRequestList.get(0);
        taxiBookingCache.remove(bookingRequest.getCustomerId());
        // TODO: could check if request is expired
        return bookingRequest;
    }

    @Override
    public BookingRequest acceptBookingRequest(BookingRequest bookingRequest) {
        BookingDO bookingDO = bookingRepository.findById(bookingRequest.getBookingId()).orElse(new BookingDO());

        // TODO invoke validation chain from here
        if (bookingDO.getBookingId() == null) {
            throw new InvalidStateException(String.format("Booking %s is not in DB"));
        }
        bookingDO.setBookingStatus(BookingStatus.TAXI_ALLOCATED);
        bookingDO.setTaxiId(bookingRequest.getTaxiId());
        bookingRepository.save(bookingDO);

        BookingRequest bookingResponse = bookingDOMapper.mapToBookingRequest(bookingDO);
        // TODO: enrich booking response with other details
        return bookingResponse;
    }


}
