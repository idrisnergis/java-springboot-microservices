package com.idrisnergis.microservice.patientservice.controller;

import com.idrisnergis.microservice.patientservice.dto.PatientRequestDTO;
import com.idrisnergis.microservice.patientservice.dto.PatientResponseDTO;
import com.idrisnergis.microservice.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/patients")
@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatient(){
        List<PatientResponseDTO> patient = patientService.getPatients();
        return ResponseEntity.ok().body(patient);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }
}
