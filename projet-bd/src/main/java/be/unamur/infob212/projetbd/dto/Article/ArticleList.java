package be.unamur.infob212.projetbd.dto.Article;

import lombok.Data;

@Data
public class ArticleList {
    private Integer id;
    private String nom;
    private String description;
    private Double prix;
}
