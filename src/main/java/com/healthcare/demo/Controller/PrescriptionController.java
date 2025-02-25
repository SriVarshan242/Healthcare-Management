package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.Prescription;
import com.healthcare.demo.Service.PrescriptionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    // GET /prescriptions?page=0&size=10&sortBy=id
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir)  {
        Page<Prescription> prescriptionPage = prescriptionService.getAllPrescriptions(page, size, sortBy,sortDir);
        return ResponseEntity.ok(prescriptionPage.getContent());
    }

    // GET /prescriptions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /prescriptions
    @PostMapping
    public ResponseEntity<Prescription> createOrUpdatePrescription(@RequestBody Prescription prescription) {
        Prescription savedPrescription = prescriptionService.savePrescription(prescription);
        return ResponseEntity.ok(savedPrescription);
    }

    // PUT /prescriptions/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        if (!prescriptionService.getPrescriptionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prescription.setId(id);
        Prescription updatedPrescription = prescriptionService.savePrescription(prescription);
        return ResponseEntity.ok(updatedPrescription);
    }

    // DELETE /prescriptions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok("{ \"message\": \"Prescription deleted successfully\" }");
    }
}
