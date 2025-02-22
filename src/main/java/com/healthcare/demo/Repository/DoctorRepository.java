package com.healthcare.demo.Repository;

import com.healthcare.demo.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Additional custom query methods can be defined here if needed.
}
