package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Article.ArticleList;
import be.unamur.infob212.projetbd.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<ArticleList>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }
}