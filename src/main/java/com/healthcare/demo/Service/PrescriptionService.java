package com.healthcare.demo.Service;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrescriptionService {

    private PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    // Create or update a prescription
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    // Retrieve a prescription by ID
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    // Retrieve all prescriptions with pagination and sorting (if needed)
    public Page<Prescription> getAllPrescriptions(int page, int size, String sortBy,String sortDir) {
        Sort sort = sortDir.equals("asc")? Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(page, size,sort);
        return prescriptionRepository.findAll(pageable);
    }

    // Delete a prescription by ID
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
}
