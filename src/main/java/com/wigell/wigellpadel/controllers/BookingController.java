package com.wigell.wigellpadel.controllers;
import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v5/")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mybookings")
    public ResponseEntity<Map<String, List<Booking>>> getAllBookings(@RequestParam Long customerId) {
        Map<String, List<Booking>> bookings = bookingService.getAllBookings(customerId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

  /*  @PreAuthorize("hasRole('USER')")
    @GetMapping("/mybookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings(@RequestParam Long customerId) {

        List<BookingDTO> bookings = bookingService.getAllBookings(customerId);

        return new ResponseEntity<>(bookings, HttpStatus.OK);
    } */


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/booking")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.saveBooking(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/bookings/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, booking);
            if (updatedBooking != null) {
                return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}