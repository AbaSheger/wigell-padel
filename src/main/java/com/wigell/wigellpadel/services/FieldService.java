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

        boolean exists = fieldRepository.existsById(fieldId);
        if (!exists) {

            logger.info("Attempted to delete a field that does not exist with id: " + fieldId);
            throw new RuntimeException("Field not found with id: " + fieldId);
        }

        List<Booking> bookings = bookingRepository.findAllByFieldId(fieldId);


        if (!bookings.isEmpty()) {
            bookingRepository.deleteAll(bookings);
            logger.info("Deleted all bookings associated with field id: " + fieldId);
        }

        fieldRepository.deleteById(fieldId);
        logger.info("Deleted field with id: " + fieldId);
    }



    public Field updateField(Long fieldId, Field fieldDetails) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found with id: " + fieldId));
        field.setName(fieldDetails.getName());
        logger.info("Updating field with id: " + fieldId);
        return fieldRepository.save(field);
    }


    public List<String> getAllFieldNames() {
        List<String> fieldNames = fieldRepository.findAll().stream()
                .map(Field::getName)
                .collect(Collectors.toList());

        return fieldNames;
    }



    public boolean existsById(Long id) {
        return fieldRepository.existsById(id);
    }

}