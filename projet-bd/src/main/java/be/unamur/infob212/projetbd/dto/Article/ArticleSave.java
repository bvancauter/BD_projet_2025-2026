package be.unamur.infob212.projetbd.dto.Article;

import lombok.Data;

@Data
public class ArticleSave {
    private String nom;
    private String description;
    private Double prix;

    private String auteur;
    private String isbn;

    private String taille;

    private String plateforme;
    private Integer pegi;

    private String marque;
}
