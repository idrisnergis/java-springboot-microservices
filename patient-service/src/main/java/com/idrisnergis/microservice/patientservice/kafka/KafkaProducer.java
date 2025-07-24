package com.idrisnergis.microservice.patientservice.kafka;

import com.idrisnergis.microservice.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import  patient.events.PatientEvent;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private  final KafkaTemplate<String , byte[]> kafkaTemplate;

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }

}
