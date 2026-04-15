package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.model.DemandeRemboursement;
import be.unamur.infob212.projetbd.repository.DemandeRemboursementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeRemboursementService {

    private final DemandeRemboursementRepository repository;

    public DemandeRemboursementService(DemandeRemboursementRepository repository) {
        this.repository = repository;
    }

    public List<DemandeRemboursement> getAll() {
        return repository.findAll();
    }

    public DemandeRemboursement getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DemandeRemboursement non trouvée : " + id));
    }

    public DemandeRemboursement create(DemandeRemboursement d) {
        return repository.save(d);
    }

    public DemandeRemboursement update(Integer id, DemandeRemboursement updated) {
        DemandeRemboursement existing = getById(id);
        existing.setCommande(updated.getCommande());
        existing.setDate(updated.getDate());
        existing.setRaison(updated.getRaison());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
