package com.wigell.wigellpadel.services;


import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.TimeSlot;
import com.wigell.wigellpadel.repositories.BookingRepository;
import com.wigell.wigellpadel.repositories.TimeSlotRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {


    private static final Logger logger = Logger.getLogger(AvailabilityService.class);

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private BookingRepository bookingRepository;



   /* public Map<String, List<String>> getAvailableSlots(String date) {
        List<Booking> bookings = bookingRepository.findByDate(date);
        List<String> bookedSlots = bookings.stream()
                .map(booking -> booking.getField().getName() + " at " + booking.getTime())
                .collect(Collectors.toList());

        List<String> allSlots = generateAllSlots();
        List<String> availableSlots = new ArrayList<>(allSlots);
        availableSlots.removeAll(bookedSlots);

        String title = "Available fields for " + date + ":";

        Map<String, List<String>> result = new HashMap<>();
        result.put(title, availableSlots);

        return result;
    }*/


    public Map<String, List<String>> getAvailableSlots(String date) {
        List<Booking> bookings = bookingRepository.findByDate(date);
        List<String> bookedSlots = bookings.stream()
                .filter(booking -> fieldService.existsById(booking.getField().getId())) // Filter out bookings for fields that no longer exist
                .map(booking -> booking.getField().getName() + " at " + booking.getTime())
                .collect(Collectors.toList());

        List<String> allSlots = generateAllSlots(); // Ensure this method also checks for field existence
        List<String> availableSlots = new ArrayList<>(allSlots);
        availableSlots.removeAll(bookedSlots);

        String title = "Available fields for " + date + ":";

        Map<String, List<String>> result = new HashMap<>();
        result.put(title, availableSlots);

        return result;
    }



   /* private List<String> generateAllSlots() {
        List<String> allSlots = new ArrayList<>();
        List<String> fields = fieldService.getAllFieldNames();
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        for (String field : fields) {
            for (TimeSlot timeSlot : timeSlots) {
                allSlots.add(field + " at " + timeSlot.getTime());
            }
        }
        return allSlots;
    } */



 /*   public Map<String, List<String>> getAvailableSlots(String date) {
        List<Booking> bookings = bookingRepository.findByDate(date);
        // Log current bookings
       // logger.info("Bookings for date " + date + ": " + bookings);

        List<String> bookedSlots = bookings.stream()
                .filter(booking -> fieldService.existsById(booking.getField().getId())) // Filter out bookings for fields that no longer exist
                .map(booking -> booking.getField().getName() + " at " + booking.getTime())
                .collect(Collectors.toList());

        // Log booked slots after filtering
        //logger.info("Booked slots after filtering: " + bookedSlots);

        List<String> allSlots = generateAllSlots(); // Ensure this method also checks for field existence

        // Log all slots generated
        //logger.info("All slots generated: " + allSlots);

        List<String> availableSlots = new ArrayList<>(allSlots);
        availableSlots.removeAll(bookedSlots);

        // Log available slots after removing booked ones
        //logger.info("Available slots after removing booked ones: " + availableSlots);

        String title = "Available fields for " + date + ":";

        Map<String, List<String>> result = new HashMap<>();
        result.put(title, availableSlots);

        logger.info("Available slots calculated for date: " + date);
        return result;
    } */

    private List<String> generateAllSlots() {
        List<String> allSlots = new ArrayList<>();
        List<String> fields = fieldService.getAllFieldNames();

        // Log current fields
        logger.info("Fields currently in the system: " + fields);

        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        for (String field : fields) {
            for (TimeSlot timeSlot : timeSlots) {
                allSlots.add(field + " at " + timeSlot.getTime());
            }
        }

        // Log all slots generated
        logger.info("Generated all slots for fields: " + fields);
        return allSlots;
    }


}
