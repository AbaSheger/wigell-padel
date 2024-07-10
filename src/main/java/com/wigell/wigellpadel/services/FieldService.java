package com.wigell.wigellpadel.services;

import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.Field;
import com.wigell.wigellpadel.repositories.BookingRepository;
import com.wigell.wigellpadel.repositories.FieldRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void deleteField(Long fieldId) {
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

 /*   public void deleteField(Long id) {
        // Fetch the field along with associated bookings
        Field field = fieldRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Field not found"));

        // This will also delete associated bookings due to the CascadeType.ALL configuration
        fieldRepository.delete(field);
    } */

   /* @Transactional
    public void deleteField(Long id) {
        // Check if the field exists
        Field field = fieldRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Field not found"));
        logger.info("Deleting field: " + field);

        // Delete the field
        fieldRepository.delete(field);
        logger.info("Deleted field with id: " + id);

        // Ensure the field is deleted by checking the repository
        boolean exists = fieldRepository.existsById(id);
        if (exists) {
            logger.error("Field with id: " + id + " still exists after deletion attempt.");
        } else {
            logger.info("Field with id: " + id + " successfully deleted.");
        }

        // Log the current state of fields
        List<Field> fieldsAfterDeletion = fieldRepository.findAll();
        logger.info("Remaining fields after deletion: " + fieldsAfterDeletion);
    } */


   /* @Transactional
    public void deleteField(Long id) {
        if (!fieldRepository.existsById(id)) {
            logger.info("Field with id: " + id + " does not exist.");
            throw new RuntimeException("Field not found");
        }
        try {
            // Assuming there's a method to delete related bookings first
            deleteRelatedBookings(id);
            fieldRepository.deleteById(id);
            logger.info("Deleted field with id: " + id);
        } catch (Exception e) {
            logger.error("Error deleting field with id: " + id, e);
            throw e; // Rethrow or handle as appropriate
        }
    } */

   /* @Transactional
    public void deleteField(Long id) {
        // Fetch the field and associated bookings
        Field field = fieldRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Field not found"));
        List<Booking> bookings = bookingRepository.findAllByFieldId(id);

        logger.info("Deleting field: " + field);
        logger.info("Associated bookings: " + bookings);

        // Delete associated bookings first
        bookingRepository.deleteAll(bookings);
        logger.info("Deleted associated bookings for field with id: " + id);

                // Flush and clear the EntityManager to ensure the state is synchronized
                entityManager.flush();
        entityManager.clear();

        // Delete the field
        fieldRepository.delete(field);
        logger.info("Deleted field with id: " + id);

        // Flush and clear the EntityManager again to ensure the state is synchronized
        entityManager.flush();
        entityManager.clear();

        // Ensure the field is deleted by checking the repository
        boolean exists = fieldRepository.existsById(id);
        if (exists) {
            logger.error("Field with id: " + id + " still exists after deletion attempt.");
        } else {
            logger.info("Field with id: " + id + " successfully deleted.");
        }

        // Log the current state of fields
        List<Field> fieldsAfterDeletion = fieldRepository.findAll();
        logger.info("Remaining fields after deletion: " + fieldsAfterDeletion);
    } */






    private void deleteRelatedBookings(Long fieldId) {
        List<Booking> relatedBookings = bookingRepository.findByFieldId(fieldId);
        bookingRepository.deleteAll(relatedBookings);
        logger.info("Deleted all related bookings for field with id: " + fieldId);
    }


    public Field updateField(Long fieldId, Field fieldDetails) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found with id: " + fieldId));
        field.setName(fieldDetails.getName());
        logger.info("Updating field with id: " + fieldId);
        return fieldRepository.save(field);
    }

  /*  public List<String> getAllFieldNames() {
        return fieldRepository.findAll().stream()
                .map(Field::getName)
                .collect(Collectors.toList());
    }*/


    public List<String> getAllFieldNames() {
        List<String> fieldNames = fieldRepository.findAll().stream()
                .map(Field::getName)
                .collect(Collectors.toList());

        // Log field names retrieved
        logger.info("Retrieved field names: " + fieldNames);

        return fieldNames;
    }



    public boolean existsById(Long id) {
        return fieldRepository.existsById(id);
    }

}