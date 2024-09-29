package com.github.pankajyogi.spring.taxibooking.service.impl;

import com.github.pankajyogi.spring.taxibooking.db.BookingRepository;
import com.github.pankajyogi.spring.taxibooking.internal.cache.Cache;
import com.github.pankajyogi.spring.taxibooking.internal.cache.CacheManager;
import com.github.pankajyogi.spring.taxibooking.internal.cache.impl.DefaultCacheManagerImpl;
import com.github.pankajyogi.spring.taxibooking.mapper.BookingDOMapper;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.model.TaxiStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TaxiServiceImplTest {

    private BookingRepository bookingRepository;

    private CacheManager cacheManager;

    private TaxiServiceImpl taxiService;

    private BookingDOMapper bookingDOMapper;

    @BeforeEach
    public void setUp() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        bookingDOMapper = Mappers.getMapper(BookingDOMapper.class);
        cacheManager = new DefaultCacheManagerImpl();
        taxiService = new TaxiServiceImpl(bookingRepository, bookingDOMapper, cacheManager);
    }

    @Test
    public void getAvailableTaxis_shouldReturnAvailableTaxisFromCache() {
        Cache<Long, TaxiInfo> availableTaxisCache = cacheManager.getCache("AvailableTaxisCache");

        availableTaxisCache.put(1L, createTaxiInfo(1L, TaxiStatus.AVAILABLE));
        availableTaxisCache.put(2L, createTaxiInfo(2L, TaxiStatus.AVAILABLE));
        availableTaxisCache.put(3L, createTaxiInfo(3L, TaxiStatus.AVAILABLE));
        availableTaxisCache.put(4L, createTaxiInfo(4L, TaxiStatus.AVAILABLE));

        List<TaxiInfo> availableTaxis = taxiService.getAvailableTaxis();

        assertEquals(availableTaxis.size(), 4);
    }


    @Test
    public void getAvailableTaxis_givenNoTaxisAvailable_shouldReturnEmptyList() {
        List<TaxiInfo> availableTaxis = taxiService.getAvailableTaxis();

        assertTrue(availableTaxis.isEmpty());
    }

    @Test
    public void updateTaxiStatus_givenAvailableStatus_shouldBeAddedToCache() {
        taxiService.updateTaxiStatus(createTaxiInfo(1L, TaxiStatus.AVAILABLE));

        List<TaxiInfo> availableTaxis = taxiService.getAvailableTaxis();
        assertEquals(availableTaxis.size(), 1);
        assertEquals(availableTaxis.get(0).getTaxiId(), 1L);
    }

    @Test
    public void updateTaxiStatus_givenNonAvailableStatus_shouldBeRemovedFromCache() {
        taxiService.updateTaxiStatus(createTaxiInfo(1L, TaxiStatus.AVAILABLE));
        List<TaxiInfo> availableTaxis = taxiService.getAvailableTaxis();
        assertEquals(availableTaxis.size(), 1);

        taxiService.updateTaxiStatus(createTaxiInfo(1L, TaxiStatus.OUT_FOR_SERVICE));
        availableTaxis = taxiService.getAvailableTaxis();
        assertEquals(availableTaxis.size(), 0);
    }

    @Test
    public void getBookingRequest_givenBookingsAvailable_shouldReturnOneOfThem() {
        Long taxiId = 1L;
        String cacheName = taxiId + "-bookings";
        Cache<Long, BookingRequest> taxiBookingCache = cacheManager.getCache(cacheName);

        taxiBookingCache.put(1L, createBookingRequest(1L));
        taxiBookingCache.put(2L, createBookingRequest(2L));
        taxiBookingCache.put(3L, createBookingRequest(3L));

        BookingRequest bookingRequest = taxiService.getBookingRequest(taxiId);

        assertNotNull(bookingRequest);
        assertEquals(taxiBookingCache.values().size(), 2); // not 3, since we consumed one of the bookings
    }

    @Test
    public void getBookingRequest_givenNoBookings_shouldReturnNull() {
        Long taxiId = 1L;

        BookingRequest returnedBookingRequest = taxiService.getBookingRequest(taxiId);

        assertNull(returnedBookingRequest);
    }

    private TaxiInfo createTaxiInfo(Long taxiId, TaxiStatus taxiStatus) {
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTaxiId(taxiId);
        taxiInfo.setTaxiStatus(taxiStatus);
        return taxiInfo;
    }

    private BookingRequest createBookingRequest(Long customerId) {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCustomerId(customerId);
        return bookingRequest;
    }

}
