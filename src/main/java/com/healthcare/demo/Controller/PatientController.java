package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.Patient;
import com.healthcare.demo.Service.PatientService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient Controller", description = "APIs for managing patients in the healthcare system")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Get all patients", description = "Fetch paginated list of patients with sorting options")
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number, default is 0") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size, default is 10") int size,
            @RequestParam(defaultValue = "id") @Parameter(description = "Sort by field, default is id") String sortBy,
            @RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction, either asc or desc") String sortDir) {
        Page<Patient> patientPage = patientService.getAllPatients(page, size, sortBy, sortDir);
        return ResponseEntity.ok(patientPage.getContent());
    }

    @Operation(summary = "Get patient by ID", description = "Fetch a patient by their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(
            @PathVariable @Parameter(description = "ID of the patient to fetch") Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create or update a patient", description = "Create a new patient or update an existing one")
    @PostMapping
    public ResponseEntity<Patient> createOrUpdatePatient(
            @RequestBody @Parameter(description = "Patient details for creation or update") Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @Operation(summary = "Update a patient", description = "Update an existing patient's details based on ID")
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable @Parameter(description = "ID of the patient to update") Long id,
            @RequestBody @Parameter(description = "Updated patient details") Patient patient) {
        return patientService.getPatientById(id)
                .map(existingPatient -> {
                    patient.setId(id);
                    Patient updatedPatient = patientService.savePatient(patient);
                    return ResponseEntity.ok(updatedPatient);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a patient", description = "Delete a patient by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(
            @PathVariable @Parameter(description = "ID of the patient to delete") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("{ \"message\": \"Patient deleted successfully\" }");
    }
}
