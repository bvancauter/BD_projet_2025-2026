package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Avis.AvisFull;
import be.unamur.infob212.projetbd.dto.Avis.AvisSave;
import be.unamur.infob212.projetbd.service.AvisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/avis")
@RequiredArgsConstructor
public class AvisController {

    private final AvisService avisService;

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<AvisFull>> getByArticle(@PathVariable Integer articleId) {
        return ResponseEntity.ok(avisService.getByArticle(articleId));
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('COMPTABLE')")
    public ResponseEntity<List<AvisFull>> getByUtilisateur(@PathVariable Integer utilisateurId) {
        return ResponseEntity.ok(avisService.getByUtilisateur(utilisateurId));
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<AvisFull> create(@RequestBody AvisSave dto) {
        try {
            AvisFull created = avisService.create(dto);
            URI location = URI.create("/avis/article/" + created.getArticleId());
            return ResponseEntity.created(location).body(created);

        } catch (RuntimeException e) {

            if (e.getMessage().equals("UTILISATEUR_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (e.getMessage().equals("ARTICLE_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (e.getMessage().equals("COMMANDE_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (e.getMessage().equals("AVIS_ALREADY_EXISTS")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{utilisateurId}/{articleId}/{commandeId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> delete(
            @PathVariable Integer utilisateurId,
            @PathVariable Integer articleId,
            @PathVariable Integer commandeId) {
        try {
            avisService.delete(utilisateurId, articleId, commandeId);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            if (e.getMessage().equals("AVIS_NOT_FOUND")) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
