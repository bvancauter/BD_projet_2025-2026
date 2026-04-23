package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.dto.ChiffreAffaire.CaAnnuelDTO;
import be.unamur.infob212.projetbd.dto.ChiffreAffaire.CaMensuelDTO;
import be.unamur.infob212.projetbd.service.ChiffreAffaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ca")
@RequiredArgsConstructor
public class ChiffreAffaireController {
    private final ChiffreAffaireService service;

    @GetMapping("/mensuel")
    @PreAuthorize("hasRole('COMPTABLE')")
    public ResponseEntity<List<CaMensuelDTO>> getMensuel() {
        return ResponseEntity.ok(service.getMensuel());
    }

    @GetMapping("/annuel")
    @PreAuthorize("hasRole('COMPTABLE')")
    public ResponseEntity<List<CaAnnuelDTO>> getAnnuel() {
        return ResponseEntity.ok(service.getAnnuel());
    }
}
