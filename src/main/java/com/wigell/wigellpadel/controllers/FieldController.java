package com.wigell.wigellpadel.controllers;


import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.services.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v5")
public class FieldController {

    @Autowired
    private FieldService fieldService;


   /* @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addfield")
    public Field addField(@RequestBody Field field) {
        return fieldService.saveField(field);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletefield/{id}")
    public void deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateinfo")
    public Field updateField(@RequestBody Field field) {
        return fieldService.updateField(field.getId(), field);
    } */

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addfield")
    public ResponseEntity<Booking> addField(@RequestParam String field, @RequestParam Long id) {
        Booking booking = fieldService.addFieldToBooking(id, field);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletefield/{id}")
    public ResponseEntity<?> deleteField(@PathVariable Long id) {
        Booking booking = fieldService.deleteFieldFromBooking(id);
        if (booking != null) {
            return ResponseEntity.ok("Field deleted successfully from booking with id: " + id);
        } else {
            return new ResponseEntity<>("Booking not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateinfo")
    public ResponseEntity<Booking> updateField(@RequestParam String field, @RequestParam Long id){
        Booking booking = fieldService.updateFieldOfBooking(id, field);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}