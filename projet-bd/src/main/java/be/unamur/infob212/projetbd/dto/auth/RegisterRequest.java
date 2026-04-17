package be.unamur.infob212.projetbd.dto.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String prenom;
    private String nom;
    private String telephone;

    private String adrRue;
    private String adrNumero;
    private String adrVille;
    private String adrCodePostal;

    private String motDePasse;

    private String methodePaiement;
}
