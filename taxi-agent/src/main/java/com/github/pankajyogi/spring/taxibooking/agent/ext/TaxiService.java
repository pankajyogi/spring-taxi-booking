package com.github.pankajyogi.spring.taxibooking.agent.ext;

import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TaxiService {

    private static final Logger logger = LoggerFactory.getLogger(TaxiService.class);

    private final RestTemplate restTemplate;

    private final String taxiServiceUrl;

    @Autowired
    public TaxiService(@Value("{taxiServerUrl}") String taxiServerUrl, RestTemplate restTemplate) {
        this.taxiServiceUrl = taxiServerUrl;
        this.restTemplate = restTemplate;
    }

    public void updateTaxiStatus(TaxiInfo taxiInfo) {
        logger.info("Requesting server to update the taxi status");
        UriBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(taxiServiceUrl)
                .path("/api/taxis/updateStatus");
        try {
            restTemplate.put(uriBuilder.toUriString(), taxiInfo);
        } catch (Exception e) {
            logger.error("Exception during updateTaxiStatus", e);
        }
    }


    public void getBookingRequest() {
        logger.info("Requesting server to get new booking requests");
        UriBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(taxiServiceUrl)
                .path("/api/taxis/bookingRequest");
        restTemplate.getForObject(uriBuilder.toUriString(), BookingRequest.class);
    }

    public void acceptBooking(BookingRequest bookingRequest) {
        logger.info("Requesting server to accept booking");
        UriBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(taxiServiceUrl)
                .path("/api/taxis/bookingRequest");
        restTemplate.postForObject(uriBuilder.toUriString(), bookingRequest, BookingRequest.class);
    }

}
