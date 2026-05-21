package be.unamur.infob212.projetbd.dto.Rapport;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticlePopulaireDTO {
    private Integer id;
    private String nom;
    private Double prix;
    private Long quantiteVendue;
    private Long nbCommandes;
    private Double noteMoyenne;
    private Long nbAvis;
}
