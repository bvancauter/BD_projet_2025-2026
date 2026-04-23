package be.unamur.infob212.projetbd.dto.ChiffreAffaire;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaAnnuelDTO {
    private Integer annee;
    private Double chiffreAffaires;
    private Long nbCommandes;
}
