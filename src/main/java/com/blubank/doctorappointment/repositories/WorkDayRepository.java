package com.blubank.doctorappointment.repositories;

import com.blubank.doctorappointment.entities.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface WorkDayRepository extends JpaRepository<WorkDay , Integer> {
    WorkDay findWorkDayByDate(Date date);
}
