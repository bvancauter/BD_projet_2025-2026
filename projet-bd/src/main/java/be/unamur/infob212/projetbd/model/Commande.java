package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "date_livraison")
    private LocalDateTime dateLivraison;

    @Column(name = "date_annulation")
    private LocalDateTime dateAnnulation;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Column(name = "utilisateur_id")
    private Integer utilisateurId;

    public enum Statut {
        EN_ATTENTE, EXPEDIEE, LIVREE, ANNULEE
    }
}
