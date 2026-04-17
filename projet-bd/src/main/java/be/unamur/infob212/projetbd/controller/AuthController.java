package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.auth.LoginRequest;
import be.unamur.infob212.projetbd.dto.auth.RegisterRequest;
import be.unamur.infob212.projetbd.model.Utilisateur;
import be.unamur.infob212.projetbd.repository.UtilisateurRepository;
import be.unamur.infob212.projetbd.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurRepository repository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        Optional<Utilisateur> optionalUser =
                repository.findByEmail(request.getEmail());

        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur introuvable");
        }

        Utilisateur user = optionalUser.get();

        if(!passwordEncoder.matches(
                request.getMotDePasse(),
                user.getMotDePasse())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Mot de passe invalide");
        }

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email déjà utilisé");
        }

        Utilisateur user = new Utilisateur();

        user.setEmail(request.getEmail());
        user.setPrenom(request.getPrenom());
        user.setNom(request.getNom());
        user.setTelephone(request.getTelephone());

        user.setAdrRue(request.getAdrRue());
        user.setAdrNumero(request.getAdrNumero());
        user.setAdrVille(request.getAdrVille());
        user.setAdrCodePostal(request.getAdrCodePostal());

        user.setMethodePaiement(request.getMethodePaiement());

        user.setMotDePasse(
                passwordEncoder.encode(request.getMotDePasse())
        );

        user.setRole(Utilisateur.Role.CLIENT);

        repository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Utilisateur créé");
    }
}
