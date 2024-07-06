package com.wigell.wigellpadel.controllers;


import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.Field;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addfield")
    public ResponseEntity<Field> addField(@RequestBody Field field) {
        try {
            Field savedField = fieldService.saveField(field);
            return new ResponseEntity<>(savedField, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletefield/{id}")
    public ResponseEntity<?> deleteField(@PathVariable Long id) {
        try {
            fieldService.deleteField(id);
            return new ResponseEntity<>("Field deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateinfo/{id}")
    public ResponseEntity<Field> updateField(@PathVariable Long id, @RequestBody Field field) {
        try {
            Field updatedField = fieldService.updateField(id, field);
            return new ResponseEntity<>(updatedField, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}