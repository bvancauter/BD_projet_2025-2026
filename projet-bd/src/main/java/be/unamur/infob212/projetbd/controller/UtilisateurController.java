package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.model.Utilisateur;
import be.unamur.infob212.projetbd.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService service;

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Utilisateur> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Utilisateur create(@RequestBody Utilisateur u) {
        return service.create(u);
    }

    @PutMapping("/{id}")
    public Utilisateur update(@PathVariable Integer id, @RequestBody Utilisateur u) {
        return service.update(id, u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
