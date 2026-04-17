package be.unamur.infob212.projetbd.dto.Commande;

import lombok.Data;

@Data
public class LigneCommandeDto {
    private Integer articleId;
    private Integer quantite;
}
