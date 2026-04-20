package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.Promotion.PromotionFull;
import be.unamur.infob212.projetbd.dto.Promotion.PromotionList;
import be.unamur.infob212.projetbd.dto.Promotion.PromotionSave;
import be.unamur.infob212.projetbd.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENT','MARKETING','COMPTABLE')")
    public ResponseEntity<List<PromotionList>> getAll() {
        return ResponseEntity.ok(promotionService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','MARKETING','COMPTABLE')")
    public ResponseEntity<PromotionFull> get(@PathVariable Integer id) {
        Optional<PromotionFull> promo = promotionService.get(id);
        return promo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('MARKETING')")
    public ResponseEntity<PromotionFull> create(@RequestBody PromotionSave dto) {
        PromotionFull created = promotionService.create(dto);
        URI location = URI.create("/promotions/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MARKETING')")
    public ResponseEntity<PromotionFull> update(@PathVariable Integer id, @RequestBody PromotionFull dto) {
        try {
            PromotionFull updated = promotionService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MARKETING')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            promotionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
