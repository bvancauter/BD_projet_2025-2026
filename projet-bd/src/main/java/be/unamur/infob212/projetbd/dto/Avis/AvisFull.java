package be.unamur.infob212.projetbd.dto.Avis;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AvisFull {
    private Integer utilisateurId;
    private Integer articleId;
    private Integer commandeId;
    private Integer note;
    private String commentaire;
    private LocalDateTime dateAvis;
}
