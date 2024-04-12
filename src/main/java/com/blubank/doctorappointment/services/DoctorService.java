package com.blubank.doctorappointment.services;

import com.blubank.doctorappointment.entities.WorkDay;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.Date;

@Service
public class DoctorService {
    public WorkDay initialNewDay(Date date, LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(endTime)) {
            return new WorkDay(date, startTime, endTime);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endDate should be after startDate");
        }

    }

}
