package com.wigell.wigellpadel.repositories;
import com.wigell.wigellpadel.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByCustomerId(Long customerId);

    List<Booking> findByFieldNameAndDateAndTime(String fieldName, String date, String time);

    List<Booking> findByDate(String date);

    List<Booking> findAllByFieldId(Long fieldId);
}