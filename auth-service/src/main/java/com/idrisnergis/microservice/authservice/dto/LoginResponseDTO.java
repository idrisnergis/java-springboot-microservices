package com.idrisnergis.microservice.authservice.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private final String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
