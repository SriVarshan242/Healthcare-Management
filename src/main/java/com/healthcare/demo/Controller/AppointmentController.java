package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // GET /appointments?page=0&size=10&sortBy=appointmentDate
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<Appointment> appointmentPage = appointmentService.getAllAppointments(page, size, sortBy,sortDir);
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

    // PUT /appointments/{id} - Update an appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        Optional<Appointment> existingAppointment = appointmentService.getAppointmentById(id);
        if (existingAppointment.isPresent()) {
            appointment.setId(id); // Ensure the correct ID is used
            Appointment updatedAppointment = appointmentService.saveAppointment(appointment);
            return ResponseEntity.ok(updatedAppointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /appointments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("{ \"message\": \"Appointment deleted successfully\" }");
    }

    @GetMapping("/between")
    public ResponseEntity<List<Appointment>> getAppointmentsBetweenDates(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Appointment> appointments = appointmentService.getAppointmentsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }
}
