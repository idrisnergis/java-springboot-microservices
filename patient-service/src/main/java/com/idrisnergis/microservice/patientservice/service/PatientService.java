package com.idrisnergis.microservice.patientservice.service;

import com.idrisnergis.microservice.patientservice.dto.PatientRequestDTO;
import com.idrisnergis.microservice.patientservice.dto.PatientResponseDTO;
import com.idrisnergis.microservice.patientservice.mapper.PatientMapper;
import com.idrisnergis.microservice.patientservice.model.Patient;
import com.idrisnergis.microservice.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        try{
            Patient addPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
            return PatientMapper.toDto(addPatient);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the patient", ex);
        }

    }
}
