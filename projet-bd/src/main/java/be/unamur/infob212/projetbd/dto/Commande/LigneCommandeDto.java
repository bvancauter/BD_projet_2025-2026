package be.unamur.infob212.projetbd.dto.Commande;

import be.unamur.infob212.projetbd.dto.Article.ArticleList;
import lombok.Data;

@Data
public class LigneCommandeDto {
    private Integer articleId;
    private Integer quantite;
    private ArticleList article;
}
