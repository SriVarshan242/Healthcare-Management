package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // GET /appointments?page=0&size=10&sortBy=appointmentDate
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy) {
        Page<Appointment> appointmentPage = appointmentService.getAllAppointments(page, size, sortBy);
        return ResponseEntity.ok(appointmentPage.getContent());
    }

    // GET /appointments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /appointments
    @PostMapping
    public ResponseEntity<Appointment> createOrUpdateAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.saveAppointment(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    // DELETE /appointments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("{ \"message\": \"Appointment deleted successfully\" }");
    }
}
