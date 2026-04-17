package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Article.ArticleFull;
import be.unamur.infob212.projetbd.dto.Article.ArticleList;
import be.unamur.infob212.projetbd.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<ArticleList>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleFull> getArticleById(@PathVariable Integer id) {

        Optional<ArticleFull> article = articleService.getArticleById(id);

        if (article.isPresent()) {
            return ResponseEntity.ok(article.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleFull> updateArticle(@PathVariable Integer id, @RequestBody ArticleFull dto) {
        ArticleFull updated = articleService.updateArticle(id, dto);
        return ResponseEntity.ok(updated);
    }
}