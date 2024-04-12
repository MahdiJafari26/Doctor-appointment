package com.blubank.doctorappointment.repositories;

import com.blubank.doctorappointment.entities.Appointment;
import com.blubank.doctorappointment.entities.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByWorkDay(WorkDay workDay);

    Appointment findByWorkDayAndAppointmentStartTime(WorkDay workDay, LocalTime startTime);
}
