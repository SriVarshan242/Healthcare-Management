package com.healthcare.demo.Repository;

import com.healthcare.demo.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.List;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Additional custom query methods can be defined here if needed
    List<Doctor> findBySpecialization(String specialization);
    @Query("SELECT d FROM Doctor d WHERE d.name LIKE :prefix%") 
    List<Doctor> fetchByNameStartingWith(@Param("prefix") String prefix);

}
