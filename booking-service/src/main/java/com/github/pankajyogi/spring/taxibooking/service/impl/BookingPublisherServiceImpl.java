package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.internal.cache.Cache;
import com.github.pankajyogi.spring.taxibooking.internal.cache.CacheManager;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.service.BookingPublisherService;
import com.github.pankajyogi.spring.taxibooking.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingPublisherServiceImpl implements BookingPublisherService {

    private final CacheManager cacheManager;

    private final TaxiService taxiService;

    @Autowired
    public BookingPublisherServiceImpl(CacheManager cacheManager, TaxiService taxiService) {
        this.cacheManager = cacheManager;
        this.taxiService = taxiService;
    }

    @Override
    public void publishBookingRequest(BookingRequest bookingRequest) {
        List<TaxiInfo> taxiInfoList = taxiService.getAvailableTaxis();
        for (TaxiInfo taxiInfo : taxiInfoList) {
            Cache<Long, BookingRequest> taxiBookingCache = cacheManager.getCache(taxiInfo.getTaxiId()+"-bookings");
            taxiBookingCache.put(bookingRequest.getCustomerId(), bookingRequest);
        }
    }
}
