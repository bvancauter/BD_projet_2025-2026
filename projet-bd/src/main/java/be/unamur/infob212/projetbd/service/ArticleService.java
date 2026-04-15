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

    public Article getArticleById(Integer id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Integer id, Article updated) {
        Article existing = getArticleById(id);

        existing.setNom(updated.getNom());
        existing.setDescription(updated.getDescription());
        existing.setPrix(updated.getPrix());

        return articleRepository.save(existing);
    }

    public void deleteArticle(Integer id) {
        if (!articleRepository.existsById(id)) {
            throw new RuntimeException("Article non trouvé");
        }
        articleRepository.deleteById(id);
    }
}
