package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Utilisateur")
@NoArgsConstructor
@AllArgsConstructor
public class    Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer utilisateurId;

    @Column(unique = true)
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
