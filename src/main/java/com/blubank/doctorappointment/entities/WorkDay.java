package com.blubank.doctorappointment.entities;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.Vector;

@Entity
public class WorkDay {
    private int id;
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Vector<Appointment> takenAppointmentVector;

    public WorkDay() {
        this(new Date(), LocalTime.now(), LocalTime.now().plusHours(8));
    }

    public WorkDay(Date date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @OneToMany
    public Vector<Appointment> getTakenAppointmentVector() {
        return takenAppointmentVector;
    }

    public void setTakenAppointmentVector(Vector<Appointment> takenAppointmentList) {
        this.takenAppointmentVector = takenAppointmentList;
    }
}
