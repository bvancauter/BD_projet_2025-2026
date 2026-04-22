package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "DemandeRemboursement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRemboursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commande_id")
    private Integer commandeId;

    @Column(name = "date_demande")
    private LocalDateTime dateDemande;

    private String raison;
}
