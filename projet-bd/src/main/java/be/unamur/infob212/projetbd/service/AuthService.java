package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.auth.LoginRequest;
import be.unamur.infob212.projetbd.dto.auth.RegisterRequest;
import be.unamur.infob212.projetbd.model.Utilisateur;
import be.unamur.infob212.projetbd.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public void register(RegisterRequest request, Utilisateur.Role role) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("EMAIL_EXISTS");
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

        user.setRole(role);

        repository.save(user);
    }

    public String login(LoginRequest request) {

        Utilisateur user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(
                request.getMotDePasse(),
                user.getMotDePasse())) {
            throw new RuntimeException("BAD_PASSWORD");
        }

        return jwtService.generateToken(user.getEmail());
    }
}
