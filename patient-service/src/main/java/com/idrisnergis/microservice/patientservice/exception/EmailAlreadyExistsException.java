package com.idrisnergis.microservice.patientservice.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message){super(message);}
}