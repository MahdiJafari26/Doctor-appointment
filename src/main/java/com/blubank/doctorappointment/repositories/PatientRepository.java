package com.blubank.doctorappointment.repositories;

import com.blubank.doctorappointment.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByPhoneNumber(String phoneNumber);
}
