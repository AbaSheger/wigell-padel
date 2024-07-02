package com.wigell.wigellpadel.services;

import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.repositories.BookingRepository;
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
    private RestTemplate externalRestTemplate;



    private final String currencyApiUrl = "https://api.exchangerate-api.com/v4/latest/SEK";

    public Map<String, List<Booking>> getAllBookings(Long customerId) {
        List<Booking> allBookings = bookingRepository.findAll().stream()
                // Ensure booking.getCustomer() is not null before comparing IDs
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


    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }


    public Booking saveBooking(Booking booking) {
        try {
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
            Booking existingBooking = bookingRepository.findById(id).orElse(null);
            if (existingBooking != null) {
                existingBooking.setField(booking.getField());
                existingBooking.setDate(booking.getDate());
                existingBooking.setTime(booking.getTime());
                existingBooking.setTotalPriceSEK(booking.getTotalPriceSEK());
                double conversionRate = getConversionRate();
                existingBooking.setTotalPriceEuro(booking.getTotalPriceSEK() * conversionRate);
                existingBooking.setNumberOfPlayers(booking.getNumberOfPlayers());
                existingBooking.setCustomer(booking.getCustomer());
                Booking updatedBooking = bookingRepository.save(existingBooking);
                logger.info("Updated booking: " + updatedBooking);
                return updatedBooking;
            }
            return null;
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
            logger.error("Error fetching conversion rate: " + e.getMessage());
            return 0.1;
        }
    }

    private static class CurrencyResponse {
        private Map<String, Double> rates;

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
        logger.info("Deleted booking with id: " + id);
    }
}