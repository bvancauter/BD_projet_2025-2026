package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Commande.CommandeFull;
import be.unamur.infob212.projetbd.dto.Commande.CommandeList;
import be.unamur.infob212.projetbd.dto.Commande.CommandeSave;
import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoFull;
import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoSave;
import be.unamur.infob212.projetbd.service.CommandeService;
import be.unamur.infob212.projetbd.service.UsagePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;
    private final UsagePromoService usagePromoService;


    @GetMapping
    @PreAuthorize("hasRole('COMPTABLE')")
    public ResponseEntity<List<CommandeList>> getAll() {
        return ResponseEntity.ok(commandeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeFull> get(@PathVariable Integer id) {
        Optional<CommandeFull> commande = commandeService.get(id);

        if (commande.isPresent()) {
            return ResponseEntity.ok(commande.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<CommandeList>> getByUtilisateur(@PathVariable Integer utilisateurId) {
        return ResponseEntity.ok(commandeService.getByUtilisateur(utilisateurId));
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CommandeFull> create(@RequestBody CommandeSave dto, Authentication authentication) {
        try {
            String email = authentication.getName();

            CommandeFull created = commandeService.create(dto, email);

            URI location = URI.create("/commandes/" + created.getId());

            return ResponseEntity.created(location).body(created);

        } catch (RuntimeException e) {

            if (e.getMessage().equals("UTILISATEUR_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (e.getMessage().equals("ARTICLE_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (e.getMessage().equals("LIGNES_EMPTY")) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CommandeFull> annuler(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(commandeService.annuler(id));

        } catch (RuntimeException e) {

            if (e.getMessage().equals("COMMANDE_NOT_FOUND")) {
                return ResponseEntity.notFound().build();
            }
            if (e.getMessage().equals("COMMANDE_NON_ANNULABLE")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{commandeId}/promotions")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<UsagePromoFull> applyPromotion(
            @PathVariable Integer commandeId,
            @RequestBody UsagePromoSave dto) {

        dto.setCommandeId(commandeId);
        return ResponseEntity.ok(usagePromoService.create(dto));
    }

    @GetMapping("/{commandeId}/promotions")
    public ResponseEntity<List<UsagePromoFull>> getPromotionsForCommande(
            @PathVariable Integer commandeId) {

        return ResponseEntity.ok(usagePromoService.getByCommande(commandeId));
    }
}
