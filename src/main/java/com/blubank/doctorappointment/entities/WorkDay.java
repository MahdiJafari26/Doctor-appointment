package com.blubank.doctorappointment.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class WorkDay {
    private int id;
    private Date date;
    private List<Appointment> takenAppointmentList;

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

    @OneToMany
    public List<Appointment> getTakenAppointmentList() {
        return takenAppointmentList;
    }

    public void setTakenAppointmentList(List<Appointment> takenAppointmentList) {
        this.takenAppointmentList = takenAppointmentList;
    }
}
