package com.wigell.wigellpadel.services;


import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.repositories.BookingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

    private static final Logger logger = Logger.getLogger(AvailabilityService.class);

    @Autowired
    private BookingRepository bookingRepository;



    public boolean isSlotAvailable(String field, String date, String time) {
        List<Booking> bookings = bookingRepository.findByFieldAndDateAndTime(field, date, time);
        logger.info("Checking availability for field: " + field + " date: " + date + " time: " + time);
        return bookings.isEmpty();
    }



}
