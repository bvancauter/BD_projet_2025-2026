package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemboursementRepository extends JpaRepository<Remboursement, Integer> {
}
