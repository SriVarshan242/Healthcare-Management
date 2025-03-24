package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.Prescription;
import com.healthcare.demo.Service.PrescriptionService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescriptions")
@Tag(name = "Prescription Controller", description = "APIs for managing prescriptions in the healthcare system")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Operation(summary = "Get all prescriptions", description = "Fetch paginated list of prescriptions with sorting options")
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number, default is 0") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size, default is 10") int size,
            @RequestParam(defaultValue = "id") @Parameter(description = "Sort by field, default is id") String sortBy,
            @RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction, either asc or desc") String sortDir) {
        Page<Prescription> prescriptionPage = prescriptionService.getAllPrescriptions(page, size, sortBy, sortDir);
        return ResponseEntity.ok(prescriptionPage.getContent());
    }

    @Operation(summary = "Get prescription by ID", description = "Fetch a prescription by its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(
            @PathVariable @Parameter(description = "ID of the prescription to fetch") Long id) {
        return prescriptionService.getPrescriptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create or update a prescription", description = "Create a new prescription or update an existing one")
    @PostMapping
    public ResponseEntity<Prescription> createOrUpdatePrescription(
            @RequestBody @Parameter(description = "Prescription details for creation or update") Prescription prescription) {
        Prescription savedPrescription = prescriptionService.savePrescription(prescription);
        return ResponseEntity.ok(savedPrescription);
    }

    @Operation(summary = "Update a prescription", description = "Update an existing prescription's details based on ID")
    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable @Parameter(description = "ID of the prescription to update") Long id,
            @RequestBody @Parameter(description = "Updated prescription details") Prescription prescription) {
        if (!prescriptionService.getPrescriptionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prescription.setId(id);
        Prescription updatedPrescription = prescriptionService.savePrescription(prescription);
        return ResponseEntity.ok(updatedPrescription);
    }

    @Operation(summary = "Delete a prescription", description = "Delete a prescription by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(
            @PathVariable @Parameter(description = "ID of the prescription to delete") Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok("{ \"message\": \"Prescription deleted successfully\" }");
    }
}
