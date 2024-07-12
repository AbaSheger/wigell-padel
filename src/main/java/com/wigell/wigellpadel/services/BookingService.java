package com.wigell.wigellpadel.services;

import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.Customer;
import com.wigell.wigellpadel.entities.Field;
import com.wigell.wigellpadel.repositories.BookingRepository;
import com.wigell.wigellpadel.repositories.CustomerRepository;
import com.wigell.wigellpadel.repositories.FieldRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final Logger logger = Logger.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FieldRepository fieldRepository;


    @Autowired
    private RestTemplate externalRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private final String currencyApiUrl = "https://api.exchangerate-api.com/v4/latest/SEK";

    public Map<String, List<Booking>> getAllBookings(Long customerId) {
        List<Booking> allBookings = bookingRepository.findAll().stream()
                .filter(booking -> booking.getCustomer() != null && booking.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());

        List<Booking> previousBookings = allBookings.stream()
                .filter(booking -> LocalDate.parse(booking.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(LocalDate.now()))
                .collect(Collectors.toList());

        List<Booking> activeBookings = allBookings.stream()
                .filter(booking -> LocalDate.parse(booking.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.now()) || LocalDate.parse(booking.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isEqual(LocalDate.now()))
                .collect(Collectors.toList());

        Map<String, List<Booking>> bookingsMap = new HashMap<>();
        bookingsMap.put("previousBookings", previousBookings);
        bookingsMap.put("activeBookings", activeBookings);

        return bookingsMap;
    }



    public Booking saveBooking(Booking booking) {
        try {

            if (booking.getField() == null || booking.getField().getId() == null) {
                throw new IllegalArgumentException("Field ID must be provided");
            }

            Field field = fieldRepository.findById(booking.getField().getId())
                    .orElseThrow(() -> new RuntimeException("Field not found"));
            booking.setField(field);


            if (booking.getCustomer() == null || booking.getCustomer().getId() == null) {
                throw new IllegalArgumentException("Customer ID must be provided");
            }

            Customer customer = customerRepository.findById(booking.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            booking.setCustomer(customer);


            double conversionRate = getConversionRate();
            booking.setTotalPriceEuro(booking.getTotalPriceSEK() * conversionRate);

            Booking savedBooking = bookingRepository.save(booking);
            logger.info("Created new booking: " + savedBooking);
            return savedBooking;
        } catch (Exception e) {
            logger.error("Error saving booking: " + e.getMessage());
            throw e;
        }
    }

    public Booking updateBooking(Long id, Booking booking) {
        try {
            Booking existingBooking = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            Field field = fieldRepository.findById(booking.getField().getId())
                    .orElseThrow(() -> new RuntimeException("Field not found"));
            existingBooking.setField(field);


            Customer customer = customerRepository.findById(booking.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            existingBooking.setCustomer(customer);

            existingBooking.setDate(booking.getDate());
            existingBooking.setTime(booking.getTime());
            existingBooking.setTotalPriceSEK(booking.getTotalPriceSEK());
            double conversionRate = getConversionRate();
            existingBooking.setTotalPriceEuro(booking.getTotalPriceSEK() * conversionRate);
            existingBooking.setNumberOfPlayers(booking.getNumberOfPlayers());
            Booking updatedBooking = bookingRepository.save(existingBooking);
            logger.info("Updated booking: " + updatedBooking);
            return updatedBooking;
        } catch (Exception e) {
            logger.error("Error updating booking: " + e.getMessage());
            throw new RuntimeException("Update operation failed", e);
        }
    }

    private double getConversionRate() {
        try {
            CurrencyResponse response = externalRestTemplate.getForObject(currencyApiUrl, CurrencyResponse.class);
            return response != null ? response.getRates().get("EUR") : 0.1;
        } catch (RestClientException e) {
            throw new RuntimeException("Error fetching conversion rate: " + e.getMessage(), e);
        }
    }



    private static class CurrencyResponse {
        private Map<String, Double> rates;

        public Map<String, Double> getRates() {
            return rates;
        }

    }

}