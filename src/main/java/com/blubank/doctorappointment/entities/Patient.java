package com.blubank.doctorappointment.entities;

import javax.persistence.*;
import java.util.Vector;

@Entity
public class Patient {
    private int id;
    private String phoneNumber;
    private Vector<Appointment> appointmentVector;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany
    public Vector<Appointment> getAppointmentVector() {
        return appointmentVector;
    }

    public void setAppointmentVector(Vector<Appointment> appointmentList) {
        this.appointmentVector = appointmentList;
    }
}
