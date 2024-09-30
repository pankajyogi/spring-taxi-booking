package com.github.pankajyogi.spring.taxibooking.agent;

import com.github.pankajyogi.spring.taxibooking.agent.ext.TaxiService;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.TaxiInfo;
import com.github.pankajyogi.spring.taxibooking.model.TaxiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AgentCommandService {

    private final TaxiService taxiService;

    private final Long taxiId;

    @Autowired
    public AgentCommandService(@Value("${taxiId}")Long taxiId, TaxiService taxiService) {
        this.taxiId = taxiId;
        this.taxiService = taxiService;
    }

    public void updateBooked() {
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTaxiId(taxiId);
        taxiInfo.setTaxiStatus(TaxiStatus.BOOKED);
        taxiService.updateTaxiStatus(taxiInfo);
    }

    public void updateAvailable() {
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTaxiId(taxiId);
        taxiInfo.setTaxiStatus(TaxiStatus.AVAILABLE);
        taxiService.updateTaxiStatus(taxiInfo);
    }

    public void checkNewBookings() {
        BookingRequest bookingRequest = taxiService.getBookingRequest();
        if (bookingRequest == null) {
            System.out.println("No bookings available.");
            return;
        }
        //else
        System.out.println(bookingRequest);
        System.out.println("Do you want to accept this booking[Y/n]?");
        String input = AgentApplication.scanner.next();
        if (input.equalsIgnoreCase("Y")) {
            taxiService.acceptBooking(bookingRequest);
        }
    }
}
