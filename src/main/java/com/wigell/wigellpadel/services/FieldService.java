package com.wigell.wigellpadel.services;

import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.Field;
import com.wigell.wigellpadel.repositories.BookingRepository;
import com.wigell.wigellpadel.repositories.FieldRepository;
import jakarta.persistence.EntityNotFoundException;
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

   /* public void deleteField(Long fieldId) {
        try {

            List<Booking> bookings = bookingRepository.findAllByFieldId(fieldId);


            if (!bookings.isEmpty()) {
                bookingRepository.deleteAll(bookings);
                logger.info("Deleted all bookings associated with field id: " + fieldId);
            } else {
                logger.info("No bookings found for field id: " + fieldId + ", proceeding to delete field.");
            }

            fieldRepository.deleteById(fieldId);
            logger.info("Deleted field with id: " + fieldId);
        } catch (Exception e) {
            logger.error("Error deleting field with id: " + fieldId + ". Error: " + e.getMessage());
            throw e;
        }
    } */

   /* public void deleteField(Long fieldId) {
        // Fetch all bookings associated with the field
        List<Booking> bookings = bookingRepository.findAllByFieldId(fieldId);

        // Delete all bookings associated with the field
        if (!bookings.isEmpty()) {
            bookingRepository.deleteAll(bookings);
            logger.info("Deleted all bookings associated with field id: " + fieldId);
        } else {
            logger.info("No bookings found for field id: " + fieldId + ", proceeding to delete field.");
        }

        // Delete the field itself
        fieldRepository.deleteById(fieldId);
        logger.info("Deleted field with id: " + fieldId);
    }
    */


   /* public void deleteField(Long fieldId) {
        // Check if the field exists
        boolean exists = fieldRepository.existsById(fieldId);
        if (!exists) {
            // Log and possibly throw an exception or return a response
            logger.info("Attempted to delete a field that does not exist with id: " + fieldId);
            throw new RuntimeException("Field not found with id: " + fieldId);
        }

        // If the field exists, proceed with deletion
        // Fetch all bookings associated with the field
        List<Booking> bookings = bookingRepository.findAllByFieldId(fieldId);

        // Delete all bookings associated with the field
        if (!bookings.isEmpty()) {
            bookingRepository.deleteAll(bookings);
            logger.info("Deleted all bookings associated with field id: " + fieldId);
        }

        // Delete the field itself
        fieldRepository.deleteById(fieldId);
        logger.info("Deleted field with id: " + fieldId);
    } */

    public void deleteField(Long id) {
        // Fetch the field along with associated bookings
        Field field = fieldRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Field not found"));

        // This will also delete associated bookings due to the CascadeType.ALL configuration
        fieldRepository.delete(field);
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



    public boolean existsById(Long id) {
        return fieldRepository.existsById(id);
    }

}