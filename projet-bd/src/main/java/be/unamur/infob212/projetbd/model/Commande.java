package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "Commande")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commandId;

    private String datePaiement;
    private Date dateLivraison;
    private Date dateAnnulation;

    private String statut;

    @ManyToOne
    @JoinColumn(name = "utilisateurId")
    private Utilisateur utilisateur;
}
