package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.entities.Appointment;
import com.blubank.doctorappointment.services.DoctorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/newDay")
    private ResponseEntity<String> initialNewDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestParam String startTime, @RequestParam String endTime) {
        try {
            int size = doctorService.initialNewDay(date, LocalTime.parse(startTime), LocalTime.parse(endTime)).getAppointmentList().size();
            return new ResponseEntity<>(size + " appointments has been created", HttpStatus.CREATED);
        } catch (ResponseStatusException exception) {
            return new ResponseEntity<>(exception.getReason(), exception.getStatus());
        }
    }

    @PostMapping("/getAllAppointments")
    private List<Appointment> getAllAppointments(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return doctorService.findAppointmentListByWorkDayDate(date);
    }

    @PostMapping("/disableAppointment")
    private ResponseEntity<String> disableAppointment(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestParam String appointmentTime) {
        try {
            Appointment appointment = doctorService.disableAppointment(date, LocalTime.parse(appointmentTime));
            return new ResponseEntity<>(date + " at " + appointmentTime + " changed to unavailable", HttpStatus.OK);
        } catch (ResponseStatusException exception) {
            return new ResponseEntity<>(exception.getReason(), exception.getStatus());
        }
    }

}
