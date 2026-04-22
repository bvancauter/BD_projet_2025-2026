package be.unamur.infob212.projetbd.dto.Remboursement;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemboursementFull {
    private Integer id;
    private Integer demandeRemboursementId;
    private LocalDateTime dateRemboursement;
}
