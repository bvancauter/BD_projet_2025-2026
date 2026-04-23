package be.unamur.infob212.projetbd.dto.Remboursement;

import be.unamur.infob212.projetbd.dto.Commande.CommandeFull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DemandeRemboursementFull {
    private Integer id;
    private LocalDateTime dateDemande;
    private String raison;
    private CommandeFull commande;
    private RemboursementFull remboursement;
}
