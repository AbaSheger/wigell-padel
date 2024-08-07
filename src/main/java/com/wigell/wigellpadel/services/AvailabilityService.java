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




    public Map<String, List<String>> getAvailableSlots(String date) {
        List<Booking> bookings = bookingRepository.findByDate(date);
        List<String> bookedSlots = bookings.stream()
                .filter(booking -> fieldService.existsById(booking.getField().getId()))
                .map(booking -> booking.getField().getName() + " at " + booking.getTime())
                .collect(Collectors.toList());

        List<String> allSlots = generateAllSlots();
        List<String> availableSlots = new ArrayList<>(allSlots);
        availableSlots.removeAll(bookedSlots);

        String title = "Available fields for " + date + ":";

        Map<String, List<String>> result = new HashMap<>();
        result.put(title, availableSlots);

        return result;
    }

    private List<String> generateAllSlots() {
        List<String> allSlots = new ArrayList<>();
        List<String> fields = fieldService.getAllFieldNames();


        logger.info("Fields currently in the system: " + fields);

        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        for (String field : fields) {
            for (TimeSlot timeSlot : timeSlots) {
                allSlots.add(field + " at " + timeSlot.getTime());
            }
        }


        logger.info("Generated all slots for fields: " + fields);
        return allSlots;
    }


}
