package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Rapport.ArticlePopulaireDTO;
import be.unamur.infob212.projetbd.dto.Rapport.ClientActifDTO;
import be.unamur.infob212.projetbd.dto.Rapport.VueRemboursementDTO;
import be.unamur.infob212.projetbd.service.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rapport")
@RequiredArgsConstructor
public class RapportController {

    private final RapportService rapportService;

    @GetMapping("/articles-populaires")
    @PreAuthorize("hasRole('MARKETING')")
    public ResponseEntity<List<ArticlePopulaireDTO>> getArticlesPopulaires() {
        return ResponseEntity.ok(rapportService.getArticlesPopulaires());
    }

    @GetMapping("/clients-actifs")
    @PreAuthorize("hasRole('MARKETING')")
    public ResponseEntity<List<ClientActifDTO>> getClientsActifs() {
        return ResponseEntity.ok(rapportService.getClientsActifs());
    }

    @GetMapping("/remboursements")
    @PreAuthorize("hasRole('COMPTABLE')")
    public ResponseEntity<List<VueRemboursementDTO>> getRemboursements() {
        return ResponseEntity.ok(rapportService.getRemboursements());
    }
}
