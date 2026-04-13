package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
