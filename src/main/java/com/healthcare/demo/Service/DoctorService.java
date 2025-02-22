package com.healthcare.demo.Service;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Create or update a doctor
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Get a doctor by ID
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    // Get all doctors with pagination and sorting (Week-6 requirement)
    public Page<Doctor> getAllDoctors(int page, int size, String sortBy) {
        return doctorRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    // Delete a doctor by ID
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> NameStartingWithV() {
        return doctorRepository.fetchByNameStartingWith("V"); // Now dynamic
    }
}
