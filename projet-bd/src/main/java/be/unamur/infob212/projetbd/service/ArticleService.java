package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.model.Article;
import be.unamur.infob212.projetbd.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
