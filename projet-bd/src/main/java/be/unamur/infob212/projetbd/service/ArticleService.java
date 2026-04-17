package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.Article.ArticleFull;
import be.unamur.infob212.projetbd.dto.Article.ArticleList;
import be.unamur.infob212.projetbd.model.Article;
import be.unamur.infob212.projetbd.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleList> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(this::toListDto)
                .toList();
    }

    public Optional<ArticleFull> getArticleById(Integer id) {
        return articleRepository.findById(id)
                .map(this::toFullDto);
    }

    private ArticleList toListDto(Article article) {
        ArticleList dto = new ArticleList();
        dto.setId(article.getId());
        dto.setNom(article.getNom());
        dto.setDescription(article.getDescription());
        dto.setPrix(article.getPrix());

        return dto;
    }

    private ArticleFull toFullDto(Article article) {
        ArticleFull dto = new ArticleFull();

        dto.setId(article.getId());
        dto.setNom(article.getNom());
        dto.setDescription(article.getDescription());
        dto.setPrix(article.getPrix());

        dto.setAuteur(article.getAuteur());
        dto.setIsbn(article.getIsbn());
        dto.setTaille(article.getTaille());
        dto.setPlateforme(article.getPlateforme());
        dto.setPegi(article.getPegi());
        dto.setMarque(article.getMarque());

        return dto;
    }
}
