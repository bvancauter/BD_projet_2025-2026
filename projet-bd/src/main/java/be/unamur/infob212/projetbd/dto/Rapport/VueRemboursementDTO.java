package be.unamur.infob212.projetbd.dto.Rapport;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VueRemboursementDTO {
    private Integer demandeId;
    private Integer commandeId;
    private String prenom;
    private String nom;
    private String email;
    private LocalDateTime dateDemande;
    private String raison;
    private Double montantCommande;
    private String statutRemboursement;
    private LocalDateTime dateRemboursement;
}
