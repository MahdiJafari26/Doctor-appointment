package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.entities.Appointment;
import com.blubank.doctorappointment.entities.WorkDay;
import com.blubank.doctorappointment.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
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
            return new ResponseEntity<>(exception.getReason(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getAllAppointments")
    private List<Appointment> getAllAppointments(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date){
        return doctorService.findAppointmentListByWorkDayDate(date);
    }

}
