package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.entities.Appointment;
import com.blubank.doctorappointment.entities.Patient;
import com.blubank.doctorappointment.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping("/getAllAppointments")
    private List<Appointment> getAllAppointments(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return patientService.findOpenAppointmentListByWorkDayDate(date);
    }

    @PostMapping("/bookAppointment")
    private ResponseEntity<String> bookAppointment(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestParam String appointmentTime, @RequestParam String phoneNumber, @RequestParam String name) {
        if (phoneNumber.trim().length() < 1 || name.trim().length() < 1) {
            return new ResponseEntity<>("Fill the fields correctly", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            patientService.bookAppointment(date, LocalTime.parse(appointmentTime), new Patient(phoneNumber, name));
            return new ResponseEntity<>(date + " at " + appointmentTime + " is now booked for you", HttpStatus.OK);
        } catch (ResponseStatusException exception) {
            return new ResponseEntity<>(exception.getReason(), exception.getStatus());
        }
    }

    @PostMapping("/myAppointmentsList")
    private List<Appointment> getMyAppointmentsList(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestParam String phoneNumber) {
        return patientService.findMyAppointmentListByWorkDayDate(date, phoneNumber);
    }
}
