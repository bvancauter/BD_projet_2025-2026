package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.DemandeRemboursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRemboursementRepository extends JpaRepository<DemandeRemboursement, Integer> {
}
