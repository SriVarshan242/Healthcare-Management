package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.Appointment;
import com.healthcare.demo.Service.AppointmentService;

import java.time.LocalDateTime;
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
@RequestMapping("/appointments")
@Tag(name = "Appointment Controller", description = "APIs for managing appointments in the healthcare system")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Get all appointments", description = "Fetch paginated list of appointments with sorting options")
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number, default is 0") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size, default is 10") int size,
            @RequestParam(defaultValue = "appointmentDate") @Parameter(description = "Sort by field, default is appointmentDate") String sortBy,
            @RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction, either asc or desc") String sortDir) {
        Page<Appointment> appointmentPage = appointmentService.getAllAppointments(page, size, sortBy, sortDir);
        return ResponseEntity.ok(appointmentPage.getContent());
    }

    @Operation(summary = "Get appointment by ID", description = "Fetch an appointment by its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(
            @PathVariable @Parameter(description = "ID of the appointment to fetch") Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create or update an appointment", description = "Create a new appointment or update an existing one")
    @PostMapping
    public ResponseEntity<Appointment> createOrUpdateAppointment(
            @RequestBody @Parameter(description = "Appointment details for creation or update") Appointment appointment) {
        Appointment savedAppointment = appointmentService.saveAppointment(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @Operation(summary = "Update an appointment", description = "Update an existing appointment based on ID")
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable @Parameter(description = "ID of the appointment to update") Long id,
            @RequestBody @Parameter(description = "Updated appointment details") Appointment appointment) {
        Optional<Appointment> existingAppointment = appointmentService.getAppointmentById(id);
        if (existingAppointment.isPresent()) {
            appointment.setId(id); // Ensure the correct ID is used
            Appointment updatedAppointment = appointmentService.saveAppointment(appointment);
            return ResponseEntity.ok(updatedAppointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an appointment", description = "Delete an appointment by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(
            @PathVariable @Parameter(description = "ID of the appointment to delete") Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("{ \"message\": \"Appointment deleted successfully\" }");
    }

    @Operation(summary = "Get appointments between dates", description = "Fetch appointments within a specified date range")
    @GetMapping("/between")
    public ResponseEntity<List<Appointment>> getAppointmentsBetweenDates(
            @RequestParam @Parameter(description = "Start date for filtering appointments") LocalDateTime startDate,
            @RequestParam @Parameter(description = "End date for filtering appointments") LocalDateTime endDate) {
        List<Appointment> appointments = appointmentService.getAppointmentsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }
}
