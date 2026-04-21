package be.unamur.infob212.projetbd.dto.Article;

import be.unamur.infob212.projetbd.model.Article.Taille;
import lombok.Data;

@Data
public class ArticleSave {
    private String nom;
    private String description;
    private Double prix;
    private String type;
    private String auteur;
    private String isbn;

    private Taille taille;

    private String plateforme;
    private Integer pegi;

    private String marque;
}
