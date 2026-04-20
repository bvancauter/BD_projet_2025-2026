package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.model.UsagePromo;
import be.unamur.infob212.projetbd.model.UsagePromoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsagePromoRepository extends JpaRepository<UsagePromo, UsagePromoId> {

    List<UsagePromo> findByIdUtilisateurId(Integer utilisateurId);

    List<UsagePromo> findByIdCommandeId(Integer commandeId);
}
