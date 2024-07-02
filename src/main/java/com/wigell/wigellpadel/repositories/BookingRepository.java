package com.wigell.wigellpadel.repositories;
import com.wigell.wigellpadel.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByCustomerId(Long customerId);

    List<Booking> findByFieldAndDateAndTime(String field, String date, String time);
}