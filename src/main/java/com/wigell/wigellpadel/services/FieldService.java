package com.wigell.wigellpadel.services;

import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.Field;
import com.wigell.wigellpadel.repositories.BookingRepository;
import com.wigell.wigellpadel.repositories.FieldRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private static final Logger logger = Logger.getLogger(FieldService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public Field saveField(Field field) {
        logger.info("Saved field with name: " + field.getName());
        return fieldRepository.save(field);
    }

    public void deleteField(Long fieldId) {
        try {
            // Find all bookings associated with the field
            List<Booking> bookings = bookingRepository.findAllByFieldId(fieldId);

            // Delete all found bookings
            if (!bookings.isEmpty()) {
                bookingRepository.deleteAll(bookings);
                logger.info("Deleted all bookings associated with field id: " + fieldId);
            } else {
                logger.info("No bookings found for field id: " + fieldId + ", proceeding to delete field.");
            }

            // Proceed to delete the field
            fieldRepository.deleteById(fieldId);
            logger.info("Deleted field with id: " + fieldId);
        } catch (Exception e) {
            logger.error("Error deleting field with id: " + fieldId + ". Error: " + e.getMessage());
            throw e; // Rethrow the exception to handle it further up the call stack if necessary
        }
    }

    public Field updateField(Long fieldId, Field fieldDetails) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found with id: " + fieldId));
        field.setName(fieldDetails.getName());
        logger.info("Updating field with id: " + fieldId);
        return fieldRepository.save(field);
    }

    public List<String> getAllFieldNames() {
        return fieldRepository.findAll().stream()
                .map(Field::getName)
                .collect(Collectors.toList());
    }



}