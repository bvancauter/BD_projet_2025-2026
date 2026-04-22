package be.unamur.infob212.projetbd.dto.Remboursement;

import lombok.Data;

@Data
public class DemandeRemboursementSave {
    private Integer commandeId;
    private String raison;
}
