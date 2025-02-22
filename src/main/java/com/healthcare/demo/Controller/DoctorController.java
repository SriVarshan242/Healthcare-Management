package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // GET /doctors?page=0&size=10&sortBy=name
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Doctor> doctorPage = doctorService.getAllDoctors(page, size, sortBy);
        return ResponseEntity.ok(doctorPage.getContent());
    }

    // GET /doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nameWithV")
    public List<Doctor> getByNameWithV() {
        return doctorService.NameStartingWithV();
    }
    // POST /doctors (Create or update a doctor)
    @PostMapping
    public ResponseEntity<Doctor> createOrUpdateDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    // DELETE /doctors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("{ \"message\": \"Doctor deleted successfully\" }");
    }
}
