package com.wigell.wigellpadel.repositories;

import com.wigell.wigellpadel.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
}
