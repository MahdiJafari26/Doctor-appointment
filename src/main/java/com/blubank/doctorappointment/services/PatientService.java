package com.blubank.doctorappointment.services;

import com.blubank.doctorappointment.entities.Appointment;
import com.blubank.doctorappointment.entities.Patient;
import com.blubank.doctorappointment.entities.WorkDay;
import com.blubank.doctorappointment.repositories.AppointmentRepository;
import com.blubank.doctorappointment.repositories.PatientRepository;
import com.blubank.doctorappointment.repositories.WorkDayRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    final AppointmentRepository appointmentRepository;
    final WorkDayRepository workDayRepository;
    final PatientRepository patientRepository;

    public PatientService(WorkDayRepository workDayRepository, AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.workDayRepository = workDayRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<Appointment> findOpenAppointmentListByWorkDayDate(Date date) {
        WorkDay workDay = workDayRepository.findWorkDayByDate(date);
        List<Appointment> appointmentList = appointmentRepository.findAllByWorkDay(workDay);
        return appointmentList.stream().filter(appointment -> appointment.isAvailable() && !appointment.isTaken()).collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<Appointment> findMyAppointmentListByWorkDayDate(Date date , String phoneNumber) {
        Patient patient = patientRepository.findByPhoneNumber(phoneNumber);
        WorkDay workDay = workDayRepository.findWorkDayByDate(date);
        return appointmentRepository.findAllByWorkDayAndPatient(workDay , patient);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Patient getPatient(Patient patient) {
        Patient existedPatient = patientRepository.findByPhoneNumber(patient.getPhoneNumber());
        if (existedPatient == null) {
            return patientRepository.save(patient);
        }
        return existedPatient;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Appointment bookAppointment(Date date, LocalTime appointmentTime, Patient patient) {
        WorkDay workDay = workDayRepository.findWorkDayByDate(date);
        Appointment appointment = appointmentRepository.findByWorkDayAndAppointmentStartTime(workDay, appointmentTime);
        if (appointment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such an appointment");
        } else if (appointment.isTaken()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Appointment is already taken");
        } else if (!appointment.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Appointment is unavailable");
        }
        appointment.setPatient(getPatient(patient));
        appointment.setTaken(true);
        return appointmentRepository.save(appointment);
    }


}
