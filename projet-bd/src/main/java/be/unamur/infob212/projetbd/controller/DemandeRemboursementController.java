package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.model.DemandeRemboursement;
import be.unamur.infob212.projetbd.service.DemandeRemboursementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandes-remboursement")
public class DemandeRemboursementController {

    private final DemandeRemboursementService service;

    public DemandeRemboursementController(DemandeRemboursementService service) {
        this.service = service;
    }

    @GetMapping
    public List<DemandeRemboursement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DemandeRemboursement getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public DemandeRemboursement create(@RequestBody DemandeRemboursement d) {
        return service.create(d);
    }

    @PutMapping("/{id}")
    public DemandeRemboursement update(@PathVariable Integer id, @RequestBody DemandeRemboursement d) {
        return service.update(id, d);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
