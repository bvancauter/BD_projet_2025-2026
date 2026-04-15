package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.model.Remboursement;
import be.unamur.infob212.projetbd.service.RemboursementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remboursements")
public class RemboursementController {

    private final RemboursementService service;

    public RemboursementController(RemboursementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Remboursement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Remboursement getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Remboursement create(@RequestBody Remboursement r) {
        return service.create(r);
    }

    @PutMapping("/{id}")
    public Remboursement update(@PathVariable Integer id, @RequestBody Remboursement r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
