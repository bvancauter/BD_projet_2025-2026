package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.auth.LoginRequest;
import be.unamur.infob212.projetbd.dto.auth.RegisterRequest;
import be.unamur.infob212.projetbd.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        try {
            authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Utilisateur créé");

        } catch (RuntimeException e) {

            if (e.getMessage().equals("EMAIL_EXISTS")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email déjà utilisé");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        try {
            String token = authService.login(request);
            return ResponseEntity.ok(token);

        } catch (RuntimeException e) {

            if (e.getMessage().equals("USER_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Utilisateur introuvable");
            }

            if (e.getMessage().equals("BAD_PASSWORD")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Mot de passe invalide");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur");
        }
    }
}
