package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String prenom;
    private String nom;
    private String telephone;

    @Column(name = "adr_rue")
    private String adrRue;

    @Column(name = "adr_numero")
    private String adrNumero;

    @Column(name = "adr_ville")
    private String adrVille;

    @Column(name = "adr_code_postal")
    private String adrCodePostal;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "methode_paiement")
    private String methodePaiement;

    @Column(name = "est_archive")
    private boolean estArchive = false;

    public enum Role {
        CLIENT,
        COMPTABLE,
        MARKETING
    }
}
