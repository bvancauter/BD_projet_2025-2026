package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.Article.ArticleList;
import be.unamur.infob212.projetbd.dto.Commande.CommandeFull;
import be.unamur.infob212.projetbd.dto.Commande.CommandeList;
import be.unamur.infob212.projetbd.dto.Commande.CommandeSave;
import be.unamur.infob212.projetbd.dto.Commande.LigneCommandeDto;
import be.unamur.infob212.projetbd.model.Commande;
import be.unamur.infob212.projetbd.model.LigneCommande;
import be.unamur.infob212.projetbd.model.LigneCommandeId;
import be.unamur.infob212.projetbd.repository.ArticleRepository;
import be.unamur.infob212.projetbd.repository.CommandeRepository;
import be.unamur.infob212.projetbd.repository.LigneCommandeRepository;
import be.unamur.infob212.projetbd.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ArticleRepository articleRepository;
    private final UtilisateurRepository utilisateurRepository;

    public List<CommandeList> getAll() {
        return commandeRepository.findAll()
                .stream()
                .map(this::toListDto)
                .toList();
    }

    public Optional<CommandeFull> get(Integer id) {
        return commandeRepository.findById(id)
                .map(this::toFullDto);
    }

    public List<CommandeList> getByUtilisateur(Integer utilisateurId) {
        return commandeRepository.findByUtilisateurId(utilisateurId)
                .stream()
                .map(this::toListDto)
                .toList();
    }

    public CommandeFull create(CommandeSave dto) {

        if (utilisateurRepository.findById(dto.getUtilisateurId()).isEmpty()) {
            throw new RuntimeException("UTILISATEUR_NOT_FOUND");
        }

        if (dto.getLignes() == null || dto.getLignes().isEmpty()) {
            throw new RuntimeException("LIGNES_EMPTY");
        }

        for (LigneCommandeDto ligne : dto.getLignes()) {
            if (articleRepository.findById(ligne.getArticleId()).isEmpty()) {
                throw new RuntimeException("ARTICLE_NOT_FOUND");
            }
        }

        Commande commande = new Commande();
        commande.setUtilisateurId(dto.getUtilisateurId());
        commande.setDatePaiement(LocalDateTime.now());
        commande.setStatut(Commande.Statut.EN_ATTENTE);

        Commande saved = commandeRepository.save(commande);

        for (LigneCommandeDto ligne : dto.getLignes()) {
            LigneCommandeId ligneId = new LigneCommandeId(saved.getId(), ligne.getArticleId());
            ligneCommandeRepository.save(new LigneCommande(ligneId, ligne.getQuantite()));
        }

        return toFullDto(saved);
    }

    public CommandeFull annuler(Integer id) {

        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("COMMANDE_NOT_FOUND"));

        if (commande.getStatut() == Commande.Statut.LIVREE ||
                commande.getStatut() == Commande.Statut.ANNULEE) {
            throw new RuntimeException("COMMANDE_NON_ANNULABLE");
        }

        commande.setStatut(Commande.Statut.ANNULEE);
        commande.setDateAnnulation(LocalDateTime.now());

        return toFullDto(commandeRepository.save(commande));
    }

    private CommandeList toListDto(Commande commande) {
        CommandeList dto = new CommandeList();
        dto.setId(commande.getId());
        dto.setStatut(commande.getStatut().name());
        dto.setDatePaiement(commande.getDatePaiement());
        dto.setUtilisateurId(commande.getUtilisateurId());
        return dto;
    }

    private CommandeFull toFullDto(Commande commande) {
        CommandeFull dto = new CommandeFull();
        dto.setId(commande.getId());
        dto.setStatut(commande.getStatut().name());
        dto.setDatePaiement(commande.getDatePaiement());
        dto.setDateLivraison(commande.getDateLivraison());
        dto.setDateAnnulation(commande.getDateAnnulation());
        dto.setUtilisateurId(commande.getUtilisateurId());

        utilisateurRepository.findById(commande.getUtilisateurId())
                .ifPresent(u -> dto.setUtilisateurEmail(u.getEmail()));

        List<LigneCommandeDto> lignes = ligneCommandeRepository
                .findByIdCommandeId(commande.getId())
                .stream()
                .map(lc -> {
                    LigneCommandeDto l = new LigneCommandeDto();
                    l.setArticleId(lc.getId().getArticleId());
                    l.setQuantite(lc.getQuantite());

                    articleRepository.findById(lc.getId().getArticleId()).ifPresent(article -> {
                        ArticleList articleDto = new ArticleList();
                        articleDto.setId(article.getId());
                        articleDto.setNom(article.getNom());
                        articleDto.setDescription(article.getDescription());
                        articleDto.setPrix(article.getPrix());
                        l.setArticle(articleDto);
                    });

                    return l;
                })
                .toList();

        dto.setLignes(lignes);
        return dto;
    }
}
