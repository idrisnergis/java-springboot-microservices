package com.idrisnergis.microservice.patientservice.service;

import com.idrisnergis.microservice.patientservice.dto.PatientRequestDTO;
import com.idrisnergis.microservice.patientservice.dto.PatientResponseDTO;
import com.idrisnergis.microservice.patientservice.exception.EmailAlreadyExistsException;
import com.idrisnergis.microservice.patientservice.exception.PatientNotFoundException;
import com.idrisnergis.microservice.patientservice.mapper.PatientMapper;
import com.idrisnergis.microservice.patientservice.model.Patient;
import com.idrisnergis.microservice.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
            if (patientRepository.existsByEmail(patientRequestDTO.getEmail()))
                throw new EmailAlreadyExistsException("A patient with this email " + " already exists " + patientRequestDTO.getEmail());

            Patient addPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
            return PatientMapper.toDto(addPatient);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the patient", ex);
        }
    }

    public PatientResponseDTO updatePatient(UUID id , PatientRequestDTO patientRequestDTO){
        try{
            Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
            if (patientRepository.existsByEmail(patientRequestDTO.getEmail()))
                throw new EmailAlreadyExistsException("A patient with this email " + " already exists " + patientRequestDTO.getEmail());

            if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id))
                throw new EmailAlreadyExistsException("A patient with this email " + "already exists" + patientRequestDTO.getEmail());


            patient.setName(patientRequestDTO.getName());
            patient.setAddress(patientRequestDTO.getAddress());
            patient.setEmail(patientRequestDTO.getEmail());
            patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

            Patient updatePatient = patientRepository.save(patient);

            return  PatientMapper.toDto(updatePatient);

        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while update the patient", ex);
        }
    }

    public void deletePatient(UUID id) {
        try {
            patientRepository.deleteById(id);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while delete the patient", ex);
        }
    }
}
