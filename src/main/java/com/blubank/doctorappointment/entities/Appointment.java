package com.blubank.doctorappointment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Appointment {
    private int id;
    private boolean isTaken = false;
    private boolean isAvailable = true;
    private LocalTime appointmentStartTime;
    private WorkDay workDay;
    private Patient patient;

    public Appointment() {
    }

    public Appointment(LocalTime appointmentStartTime, WorkDay workDay) {
        this.appointmentStartTime = appointmentStartTime;
        this.workDay = workDay;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Column(nullable = false)
    public LocalTime getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(LocalTime appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
