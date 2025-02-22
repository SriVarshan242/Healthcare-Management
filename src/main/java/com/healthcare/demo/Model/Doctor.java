package com.healthcare.demo.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String contactInfo;

    // One-to-Many mapping with appointments (if needed)
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(Long id, String name, String specialization, String contactInfo, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contactInfo = contactInfo;
        this.appointments = appointments;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Doctor{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", specialization='" + specialization + '\'' +
               ", contactInfo='" + contactInfo + '\'' +
               '}';
    }
}
