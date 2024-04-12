package com.blubank.doctorappointment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @RequestMapping
    private String init(){
        return "hello world!";
    }
}
