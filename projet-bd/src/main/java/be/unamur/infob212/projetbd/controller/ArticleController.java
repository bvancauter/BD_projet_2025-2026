package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Article.ArticleFull;
import be.unamur.infob212.projetbd.dto.Article.ArticleList;
import be.unamur.infob212.projetbd.dto.Article.ArticleSave;
import be.unamur.infob212.projetbd.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<ArticleList>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleFull> get(@PathVariable Integer id) {

        Optional<ArticleFull> article = articleService.get(id);

        if (article.isPresent()) {
            return ResponseEntity.ok(article.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ArticleFull> createArticle(@RequestBody ArticleSave dto) {

        ArticleFull created = articleService.create(dto);
        URI location = URI.create("/articles/" + created.getId());
        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleFull> update(@PathVariable Integer id, @RequestBody ArticleFull dto) {
        ArticleFull updated = articleService.update(id, dto);
        return ResponseEntity.ok(updated);
    }
}