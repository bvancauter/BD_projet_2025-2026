package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.model.Commande;
import be.unamur.infob212.projetbd.service.CommandeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeService service;

    public CommandeController(CommandeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Commande> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Commande getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Commande create(@RequestBody Commande c) {
        return service.create(c);
    }

    @PutMapping("/{id}")
    public Commande update(@PathVariable Integer id, @RequestBody Commande c) {
        return service.update(id, c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
