package com.wigell.wigellpadel.services;


import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.TimeSlot;
import com.wigell.wigellpadel.repositories.BookingRepository;
import com.wigell.wigellpadel.repositories.TimeSlotRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AvailabilityService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private BookingRepository bookingRepository;


    public List<String> getAvailableSlots(String date) {
        List<Booking> bookings = bookingRepository.findByDate(date);
        List<String> bookedSlots = bookings.stream()
                .map(booking -> booking.getField().getName() + " at " + booking.getTime())
                .collect(Collectors.toList());

        List<String> allSlots = generateAllSlots();
        List<String> availableSlots = new ArrayList<>(allSlots);
        availableSlots.removeAll(bookedSlots);

        return availableSlots;
    }



    private List<String> generateAllSlots() {
        List<String> allSlots = new ArrayList<>();
        List<String> fields = fieldService.getAllFieldNames();
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        for (String field : fields) {
            for (TimeSlot timeSlot : timeSlots) {
                allSlots.add(field + " at " + timeSlot.getTime());
            }
        }
        return allSlots;
    }


}
