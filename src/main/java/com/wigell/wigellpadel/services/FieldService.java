package com.wigell.wigellpadel.services;

import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.repositories.BookingRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldService {

    private static final Logger logger = Logger.getLogger(FieldService.class);


    @Autowired
    BookingRepository bookingRepository;


    public Booking addFieldToBooking(Long id, String field) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setField(field);
            logger.info("Added field to booking with id: " + id);
            return bookingRepository.save(booking);
        }
        return null;
    }

     public Booking deleteFieldFromBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setField(null);
            logger.info("Deleted field from booking with id: " + id);
            return bookingRepository.save(booking);
        }
        return null;
    }



    public Booking updateFieldOfBooking(Long id, String field) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setField(field);
            logger.info("Updated field of booking with id: " + id);
            return bookingRepository.save(booking);
        }
        return null;
    }

}