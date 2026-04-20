package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.Avis;
import be.unamur.infob212.projetbd.model.AvisId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisRepository extends JpaRepository<Avis, AvisId> {

    List<Avis> findByIdArticleId(Integer articleId);

    List<Avis> findByIdUtilisateurId(Integer utilisateurId);
}
