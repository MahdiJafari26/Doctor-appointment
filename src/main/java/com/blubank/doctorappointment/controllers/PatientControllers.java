package com.blubank.doctorappointment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientControllers {

    @RequestMapping
    private String init(){
        return "hello world!";
    }
}
