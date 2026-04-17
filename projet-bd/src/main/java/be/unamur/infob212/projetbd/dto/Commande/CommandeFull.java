package be.unamur.infob212.projetbd.dto.Commande;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeFull {
    private Integer id;
    private String statut;
    private LocalDateTime datePaiement;
    private LocalDateTime dateLivraison;
    private LocalDateTime dateAnnulation;
    private Integer utilisateurId;
    private List<LigneCommandeDto> lignes;
}
