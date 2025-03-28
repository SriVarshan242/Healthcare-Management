package com.healthcare.demo.Repository;

import com.healthcare.demo.Model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // Additional custom query methods can be added here if needed.
}
