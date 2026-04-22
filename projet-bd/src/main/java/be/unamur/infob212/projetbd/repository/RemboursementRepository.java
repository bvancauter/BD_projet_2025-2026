package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RemboursementRepository extends JpaRepository<Remboursement, Integer> {

    Optional<Remboursement> findByDemandeRemboursementId(Integer demandeRemboursementId);
}
