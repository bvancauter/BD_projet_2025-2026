package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
}
