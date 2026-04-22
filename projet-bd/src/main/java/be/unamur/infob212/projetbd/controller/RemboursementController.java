package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Remboursement.DemandeRemboursementFull;
import be.unamur.infob212.projetbd.dto.Remboursement.DemandeRemboursementSave;
import be.unamur.infob212.projetbd.dto.Remboursement.RemboursementFull;
import be.unamur.infob212.projetbd.service.RemboursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/remboursements")
@RequiredArgsConstructor
public class RemboursementController {

    private final RemboursementService remboursementService;

    // COMPTABLE : voir toutes les demandes
    @GetMapping("/demandes")
    @PreAuthorize("hasRole('COMPTABLE')")
    public ResponseEntity<List<DemandeRemboursementFull>> getAllDemandes() {
        return ResponseEntity.ok(remboursementService.getAllDemandes());
    }

    // CLIENT : voir les demandes d'une commande
    @GetMapping("/demandes/commande/{commandeId}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('COMPTABLE')")
    public ResponseEntity<List<DemandeRemboursementFull>> getDemandesByCommande(@PathVariable Integer commandeId) {
        return ResponseEntity.ok(remboursementService.getDemandesByCommande(commandeId));
    }

    // CLIENT : créer une demande de remboursement
    @PostMapping("/demandes")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<DemandeRemboursementFull> createDemande(@RequestBody DemandeRemboursementSave dto) {
        try {
            DemandeRemboursementFull created = remboursementService.createDemande(dto);
            URI location = URI.create("/remboursements/demandes/commande/" + created.getCommandeId());
            return ResponseEntity.created(location).body(created);

        } catch (RuntimeException e) {

            if (e.getMessage().equals("COMMANDE_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // COMPTABLE : valider un remboursement
    @PostMapping("/demandes/{demandeId}/valider")
    @PreAuthorize("hasRole('COMPTABLE')")
    public ResponseEntity<RemboursementFull> valider(@PathVariable Integer demandeId) {
        try {
            return ResponseEntity.ok(remboursementService.validerRemboursement(demandeId));

        } catch (RuntimeException e) {

            if (e.getMessage().equals("DEMANDE_NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (e.getMessage().equals("REMBOURSEMENT_ALREADY_EXISTS")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
