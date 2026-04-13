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

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }
}
