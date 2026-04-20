package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoFull;
import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoSave;
import be.unamur.infob212.projetbd.model.UsagePromo;
import be.unamur.infob212.projetbd.model.UsagePromoId;
import be.unamur.infob212.projetbd.repository.UsagePromoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsagePromoService {

    private final UsagePromoRepository repository;

    public UsagePromoFull create(UsagePromoSave dto) {

        UsagePromoId id = new UsagePromoId(
                dto.getUtilisateurId(),
                dto.getPromotionId(),
                dto.getArticleId(),
                dto.getCommandeId()
        );

        UsagePromo usage = new UsagePromo();
        usage.setId(id);
        usage.setDateUtilisation(LocalDateTime.now());

        UsagePromo saved = repository.save(usage);
        return toFullDto(saved);
    }

    public List<UsagePromoFull> getByUtilisateur(Integer utilisateurId) {
        return repository.findByIdUtilisateurId(utilisateurId)
                .stream()
                .map(this::toFullDto)
                .toList();
    }

    public List<UsagePromoFull> getByCommande(Integer commandeId) {
        return repository.findByIdCommandeId(commandeId)
                .stream()
                .map(this::toFullDto)
                .toList();
    }

    private UsagePromoFull toFullDto(UsagePromo usage) {
        UsagePromoFull dto = new UsagePromoFull();
        dto.setUtilisateurId(usage.getId().getUtilisateurId());
        dto.setPromotionId(usage.getId().getPromotionId());
        dto.setArticleId(usage.getId().getArticleId());
        dto.setCommandeId(usage.getId().getCommandeId());
        dto.setDateUtilisation(usage.getDateUtilisation());
        return dto;
    }
}
