package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.Avis.AvisFull;
import be.unamur.infob212.projetbd.dto.Avis.AvisSave;
import be.unamur.infob212.projetbd.model.Avis;
import be.unamur.infob212.projetbd.model.AvisId;
import be.unamur.infob212.projetbd.model.Utilisateur;
import be.unamur.infob212.projetbd.repository.ArticleRepository;
import be.unamur.infob212.projetbd.repository.AvisRepository;
import be.unamur.infob212.projetbd.repository.CommandeRepository;
import be.unamur.infob212.projetbd.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvisService {

    private final AvisRepository avisRepository;
    private final ArticleRepository articleRepository;
    private final CommandeRepository commandeRepository;
    private final UtilisateurRepository utilisateurRepository;

    public List<AvisFull> getByArticle(Integer articleId) {
        return avisRepository.findByIdArticleId(articleId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<AvisFull> getByUtilisateur(Integer utilisateurId) {
        return avisRepository.findByIdUtilisateurId(utilisateurId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public AvisFull create(AvisSave dto, String email) {

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("UTILISATEUR_NOT_FOUND"));

        if (articleRepository.findById(dto.getArticleId()).isEmpty()) {
            throw new RuntimeException("ARTICLE_NOT_FOUND");
        }

        if (commandeRepository.findById(dto.getCommandeId()).isEmpty()) {
            throw new RuntimeException("COMMANDE_NOT_FOUND");
        }

        AvisId id = new AvisId(utilisateur.getId(), dto.getArticleId(), dto.getCommandeId());

        if (avisRepository.existsById(id)) {
            throw new RuntimeException("AVIS_ALREADY_EXISTS");
        }

        Avis avis = new Avis(id, dto.getNote(), dto.getCommentaire(), LocalDateTime.now());

        return toDto(avisRepository.save(avis));
    }

    public void delete(Integer utilisateurId, Integer articleId, Integer commandeId) {
        AvisId id = new AvisId(utilisateurId, articleId, commandeId);

        if (!avisRepository.existsById(id)) {
            throw new RuntimeException("AVIS_NOT_FOUND");
        }

        avisRepository.deleteById(id);
    }

    private AvisFull toDto(Avis avis) {
        AvisFull dto = new AvisFull();
        dto.setUtilisateurId(avis.getId().getUtilisateurId());
        dto.setArticleId(avis.getId().getArticleId());
        dto.setCommandeId(avis.getId().getCommandeId());
        dto.setNote(avis.getNote());
        dto.setCommentaire(avis.getCommentaire());
        dto.setDateAvis(avis.getDateAvis());
        return dto;
    }
}
