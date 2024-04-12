package com.blubank.doctorappointment.services;

import com.blubank.doctorappointment.entities.Appointment;
import com.blubank.doctorappointment.entities.WorkDay;
import com.blubank.doctorappointment.repositories.AppointmentRepository;
import com.blubank.doctorappointment.repositories.WorkDayRepository;
import com.blubank.doctorappointment.utilities.TimeUtility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class DoctorService {
    final AppointmentRepository appointmentRepository;
    final WorkDayRepository workDayRepository;

    public DoctorService(WorkDayRepository workDayRepository, AppointmentRepository appointmentRepository) {
        this.workDayRepository = workDayRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public WorkDay initialNewDay(Date date, LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(endTime)) {
            WorkDay workDay = new WorkDay(date, startTime, endTime);
            workDay.setAppointmentList(TimeUtility.breakDownWorkDay(startTime, endTime, workDay));
            workDayRepository.save(workDay);
            return workDay;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endDate should be after startDate");
        }
    }

    @Transactional(readOnly = true)
    public List<Appointment> findAppointmentListByWorkDayDate(Date date) {
        WorkDay workDay = workDayRepository.findWorkDayByDate(date);
        return appointmentRepository.findAllByWorkDay(workDay);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Appointment disableAppointment(Date date, LocalTime appointmentTime) {
        WorkDay workDay = workDayRepository.findWorkDayByDate(date);
        Appointment appointment = appointmentRepository.findByWorkDayAndAppointmentStartTime(workDay, appointmentTime);
        if (appointment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such an appointment");
        } else if (appointment.isTaken()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Appointment is taken by a patient");
        } else if (!appointment.isAvailable()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Appointment has already changed to unavailable");
        }
        appointment.setAvailable(false);
        return appointmentRepository.save(appointment);
    }

}
