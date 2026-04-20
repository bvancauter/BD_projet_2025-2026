package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}
