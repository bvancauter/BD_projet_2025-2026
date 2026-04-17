package be.unamur.infob212.projetbd.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String motDePasse;
}
