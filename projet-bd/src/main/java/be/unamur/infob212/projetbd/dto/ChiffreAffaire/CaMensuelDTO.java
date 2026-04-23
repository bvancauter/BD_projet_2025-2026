package be.unamur.infob212.projetbd.dto.ChiffreAffaire;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaMensuelDTO {
    private Integer annee;
    private Integer mois;
    private Long nbCommandes;
    private Double chiffreAffaires;
}
