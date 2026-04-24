package be.unamur.infob212.projetbd.dto.Commande;

import lombok.Data;

@Data
public class LigneCommandeSave {
    private Integer articleId;
    private Integer quantite;
}
