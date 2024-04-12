package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.entities.WorkDay;
import com.blubank.doctorappointment.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Date;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/newDay")
    private WorkDay initialNewDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestParam String startTime, @RequestParam String endTime) {
        return doctorService.initialNewDay(date, LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
}
