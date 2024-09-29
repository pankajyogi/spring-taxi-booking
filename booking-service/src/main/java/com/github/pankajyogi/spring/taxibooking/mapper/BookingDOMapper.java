package com.github.pankajyogi.spring.taxibooking.mapper;

import com.github.pankajyogi.spring.taxibooking.db.BookingDO;
import com.github.pankajyogi.spring.taxibooking.model.BookingInfo;
import com.github.pankajyogi.spring.taxibooking.model.BookingRequest;
import com.github.pankajyogi.spring.taxibooking.model.BookingResponse;
import com.github.pankajyogi.spring.taxibooking.model.LocationInfo;
import org.mapstruct.Mapper;

/**
 * BookingDOMapper is a MapStruct mapper interface for mapping between BookingDO
 * and other related data transfer objects (DTOs) such as BookingRequest,
 * BookingInfo, and BookingResponse.
 *
 * It provides methods to convert between different representations of booking
 * data.
 *
 * The @Mapper annotation is used to specify that this interface is a MapStruct
 * mapper and to configure its component model.
 */
@Mapper(componentModel = "spring")
public interface BookingDOMapper {

    BookingDO mapFrom(BookingRequest bookingRequest);

    BookingDO mapFrom(BookingInfo bookingInfo);

    BookingDO mapFrom(BookingResponse bookingResponse);

    BookingRequest mapToBookingRequest(BookingDO bookingDO);

    BookingResponse mapToBookingResponse(BookingDO bookingDO);

    BookingInfo mapToBookingInfo(BookingDO bookingDO);

    default LocationInfo mapToLocationInfo(String location) {
        if (location == null) {
            return null;
        }
        LocationInfo locationInfo = new LocationInfo();
        String[] locationComponents = location.split(",");
        locationInfo.setLatitude(Double.parseDouble(locationComponents[0]));
        locationInfo.setLongitude(Double.parseDouble(locationComponents[1]));
        return locationInfo;
    }

    default String mapToLocation(LocationInfo locationInfo) {
        if (locationInfo == null) {
            return null;
        }
        return locationInfo.getLatitude() + "," + locationInfo.getLongitude();
    }
}
