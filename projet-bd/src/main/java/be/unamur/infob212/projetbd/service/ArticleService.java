package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.Article.ArticleList;
import be.unamur.infob212.projetbd.model.Article;
import be.unamur.infob212.projetbd.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleList> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ArticleList toDto(Article article) {
        ArticleList dto = new ArticleList();
        dto.setId(article.getId());
        dto.setNom(article.getNom());
        dto.setDescription(article.getDescription());
        dto.setPrix(article.getPrix());
        
        return dto;
    }
}
