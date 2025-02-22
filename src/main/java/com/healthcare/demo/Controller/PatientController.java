package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET /patients?page=0&size=10&sortBy=name
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Patient> patientPage = patientService.getAllPatients(page, size, sortBy);
        return ResponseEntity.ok(patientPage.getContent());
    }

    // GET /patients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /patients (Create or update a patient)
    @PostMapping
    public ResponseEntity<Patient> createOrUpdatePatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    // DELETE /patients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("{ \"message\": \"Patient deleted successfully\" }");
    }
}
