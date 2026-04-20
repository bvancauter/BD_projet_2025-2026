package be.unamur.infob212.projetbd.dto.Avis;

import lombok.Data;

@Data
public class AvisSave {
    private Integer utilisateurId;
    private Integer articleId;
    private Integer commandeId;
    private Integer note;
    private String commentaire;
}
