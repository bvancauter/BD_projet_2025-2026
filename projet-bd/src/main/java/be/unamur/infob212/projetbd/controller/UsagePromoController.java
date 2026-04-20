package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoFull;
import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoSave;
import be.unamur.infob212.projetbd.service.UsagePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usage-promos")
@RequiredArgsConstructor
public class UsagePromoController {

    private final UsagePromoService service;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<UsagePromoFull> create(@RequestBody UsagePromoSave dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/utilisateur/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','MARKETING','COMPTABLE')")
    public ResponseEntity<List<UsagePromoFull>> getByUtilisateur(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getByUtilisateur(id));
    }

    @GetMapping("/commande/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','MARKETING','COMPTABLE')")
    public ResponseEntity<List<UsagePromoFull>> getByCommande(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getByCommande(id));
    }
}
