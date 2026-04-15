package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.model.Promotion;
import be.unamur.infob212.projetbd.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    public List<Promotion> getAll() {
        return repository.findAll();
    }

    public Promotion getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion non trouvée : " + id));
    }

    public Promotion create(Promotion p) {
        return repository.save(p);
    }

    public Promotion update(Integer id, Promotion updated) {
        Promotion existing = getById(id);
        existing.setNom(updated.getNom());
        existing.setDescription(updated.getDescription());
        existing.setPourcentage(updated.getPourcentage());
        existing.setDateDebut(updated.getDateDebut());
        existing.setDateFin(updated.getDateFin());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
