package com.wigell.wigellpadel.repositories;

import com.wigell.wigellpadel.entities.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
