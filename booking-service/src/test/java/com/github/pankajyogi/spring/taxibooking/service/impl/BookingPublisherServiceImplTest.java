package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.internal.cache.Cache;
import com.github.pankajyogi.spring.taxibooking.internal.cache.CacheManager;
import com.github.pankajyogi.spring.taxibooking.internal.cache.impl.DefaultCacheManagerImpl;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.service.TaxiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingPublisherServiceImplTest {

    @InjectMocks
    private BookingPublisherServiceImpl bookingPublisherService;

    @Spy
    private CacheManager cacheManager = new DefaultCacheManagerImpl();

    @Mock
    private TaxiService taxiService;

    @Test
    public void publishBookingRequest_shouldPutIntoCacheOfAvailableTaxis() {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCustomerId(1L);

        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTaxiId(1L);

        List<TaxiInfo> taxiInfoList = Collections.singletonList(taxiInfo);
        when(taxiService.getAvailableTaxis()).thenReturn(taxiInfoList);

        Cache<Long, BookingRequest> taxiBookingCache = cacheManager.getCache(taxiInfo.getTaxiId() + "-bookings");
        assertEquals(taxiBookingCache.values().size(), 0);

        bookingPublisherService.publishBookingRequest(bookingRequest);

        assertEquals(taxiBookingCache.values().size(), 1);
        verify(taxiService, times(1)).getAvailableTaxis();
    }
}