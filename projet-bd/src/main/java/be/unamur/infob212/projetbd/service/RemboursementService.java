package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.model.Remboursement;
import be.unamur.infob212.projetbd.repository.RemboursementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemboursementService {

    private final RemboursementRepository repository;

    public RemboursementService(RemboursementRepository repository) {
        this.repository = repository;
    }

    public List<Remboursement> getAll() {
        return repository.findAll();
    }

    public Remboursement getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Remboursement non trouvé : " + id));
    }

    public Remboursement create(Remboursement r) {
        return repository.save(r);
    }

    public Remboursement update(Integer id, Remboursement updated) {
        Remboursement existing = getById(id);
        existing.setDemandeRemboursement(updated.getDemandeRemboursement());
        existing.setDate(updated.getDate());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
