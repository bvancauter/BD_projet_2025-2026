package be.unamur.infob212.projetbd.dto.Remboursement;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DemandeRemboursementFull {
    private Integer id;
    private Integer commandeId;
    private LocalDateTime dateDemande;
    private String raison;
    private RemboursementFull remboursement;
}
