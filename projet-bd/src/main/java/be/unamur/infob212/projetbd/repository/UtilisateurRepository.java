package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
}
