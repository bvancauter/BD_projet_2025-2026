package be.unamur.infob212.projetbd.controller;

import be.unamur.infob212.projetbd.model.Promotion;
import be.unamur.infob212.projetbd.service.PromotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService service;

    public PromotionController(PromotionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Promotion> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Promotion getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Promotion create(@RequestBody Promotion p) {
        return service.create(p);
    }

    @PutMapping("/{id}")
    public Promotion update(@PathVariable Integer id, @RequestBody Promotion p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
