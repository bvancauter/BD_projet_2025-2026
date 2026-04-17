package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByUtilisateurId(Integer utilisateurId);
}
