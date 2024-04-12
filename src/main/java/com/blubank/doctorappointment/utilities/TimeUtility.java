package com.blubank.doctorappointment.utilities;

import com.blubank.doctorappointment.entities.Appointment;
import com.blubank.doctorappointment.entities.WorkDay;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Vector;

public final class TimeUtility {
    public static Vector<Appointment> breakDownWorkDay(LocalTime startTime, LocalTime endTime, WorkDay workDay) {
        //Using vector for thread-safety
        Vector<Appointment> appointmentVector = new Vector<>();
        long minutes = Duration.between(startTime, endTime).toMinutes();
        for (int appointmentIndex = 0; appointmentIndex < minutes/30; appointmentIndex++){
            appointmentVector.add(new Appointment(startTime.plusMinutes(30L *appointmentIndex) , workDay));
        }
        return appointmentVector;
    }
}
