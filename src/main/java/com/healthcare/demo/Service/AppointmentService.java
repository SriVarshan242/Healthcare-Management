package com.healthcare.demo.Service;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Create or update an appointment
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Retrieve an appointment by ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // Retrieve all appointments with pagination and sorting
    public Page<Appointment> getAllAppointments(int page, int size, String sortBy,String sortDir) {
        Sort sort = sortDir.equals("asc")? Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(page, size,sort);
        return appointmentRepository.findAll(pageable);
        // return appointmentRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    // Delete an appointment by ID
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findAppointmentsBetweenDates(startDate, endDate);
    }
}
