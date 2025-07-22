package com.idrisnergis.microservice.patientservice.service;

import com.idrisnergis.microservice.patientservice.dto.PatientResponseDTO;
import com.idrisnergis.microservice.patientservice.mapper.PatientMapper;
import com.idrisnergis.microservice.patientservice.model.Patient;
import com.idrisnergis.microservice.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDto).toList();
    }
}
