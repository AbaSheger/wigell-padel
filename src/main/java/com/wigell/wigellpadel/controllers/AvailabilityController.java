package com.wigell.wigellpadel.controllers;



import com.wigell.wigellpadel.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v5/")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;


   /* @GetMapping("/availability")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isSlotAvailable(String field, String date, String time) {
        return ResponseEntity.ok(availabilityService.isSlotAvailable(field, date, time));
    } */


    @GetMapping("/availability")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<String>> getAvailableSlots(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
        List<String> availableSlots = availabilityService.getAvailableSlots(date);
        if (availableSlots.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(availableSlots);
    }


}
