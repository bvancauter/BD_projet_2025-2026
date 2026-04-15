package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.model.Article;
import be.unamur.infob212.projetbd.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // GET /articles
    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

     // GET /articles/{id}
    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }

    // POST /articles
    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleService.createArticle(article);
    }

    // PUT /articles/{id}
    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable Integer id, @RequestBody Article article) {
        return articleService.updateArticle(id, article);
    }

    // DELETE /articles/{id}
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
    }
}
