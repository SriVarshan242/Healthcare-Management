package com.healthcare.demo.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medication;
    private String dosage;
    private String duration;
    private String instructions;

    // Many-to-One relationship to Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    public Prescription() {
    }

    public Prescription(Long id, String medication, String dosage, String duration, String instructions, Patient patient) {
        this.id = id;
        this.medication = medication;
        this.dosage = dosage;
        this.duration = duration;
        this.instructions = instructions;
        this.patient = patient;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Prescription{" +
               "id=" + id +
               ", medication='" + medication + '\'' +
               ", dosage='" + dosage + '\'' +
               ", duration='" + duration + '\'' +
               ", instructions='" + instructions + '\'' +
               '}';
    }
}
