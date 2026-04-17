package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.LigneCommande;
import be.unamur.infob212.projetbd.model.LigneCommandeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, LigneCommandeId> {
    List<LigneCommande> findByIdCommandeId(Integer commandeId);
}
