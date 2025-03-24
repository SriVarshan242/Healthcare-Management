package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.Doctor;
import com.healthcare.demo.Service.DoctorService;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@Tag(name = "Doctor Controller", description = "APIs for managing doctors in the healthcare system")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Get all doctors", description = "Fetch paginated list of doctors with sorting options")
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number, default is 0") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size, default is 10") int size,
            @RequestParam(defaultValue = "id") @Parameter(description = "Sort by field, default is id") String sortBy,
            @RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction, either asc or desc") String sortDir) {
        Page<Doctor> doctorPage = doctorService.getAllDoctors(page, size, sortBy, sortDir);
        return ResponseEntity.ok(doctorPage.getContent());
    }

    @Operation(summary = "Get doctor by ID", description = "Fetch a doctor by their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(
            @PathVariable @Parameter(description = "ID of the doctor to fetch") Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get doctors with names starting with V", description = "Fetch doctors whose names start with a given prefix")
    @GetMapping("/nameWithV")
    public List<Doctor> getByNameWithV(
            @RequestParam @Parameter(description = "Prefix to filter doctors by name") String prefix) {
        return doctorService.NameStartingWithV(prefix);
    }

    @Operation(summary = "Get doctors by specialization", description = "Fetch doctors by their specialization")
    @GetMapping("/specialization/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(
            @PathVariable @Parameter(description = "Specialization to filter doctors") String specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);
    }

    @Operation(summary = "Create or update a doctor", description = "Create a new doctor or update an existing one")
    @PostMapping
    public ResponseEntity<Doctor> createOrUpdateDoctor(
            @RequestBody @Parameter(description = "Doctor details for creation or update") Doctor doctor) {
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @Operation(summary = "Update a doctor", description = "Update an existing doctor's details based on ID")
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable @Parameter(description = "ID of the doctor to update") Long id,
            @RequestBody @Parameter(description = "Updated doctor details") Doctor doctor) {
        Optional<Doctor> existingDoctor = doctorService.getDoctorById(id);
        if (existingDoctor.isPresent()) {
            doctor.setId(id); // Ensure the doctor ID remains the same
            Doctor updatedDoctor = doctorService.saveDoctor(doctor);
            return ResponseEntity.ok(updatedDoctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a doctor", description = "Delete a doctor by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(
            @PathVariable @Parameter(description = "ID of the doctor to delete") Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("{ \"message\": \"Doctor deleted successfully\" }");
    }
}
