package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.model.Commande;
import be.unamur.infob212.projetbd.repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    private final CommandeRepository repository;

    public CommandeService(CommandeRepository repository) {
        this.repository = repository;
    }

    public List<Commande> getAll() {
        return repository.findAll();
    }

    public Commande getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée : " + id));
    }

    public Commande create(Commande c) {
        return repository.save(c);
    }

    public Commande update(Integer id, Commande updated) {
        Commande existing = getById(id);
        existing.setDatePaiement(updated.getDatePaiement());
        existing.setDateLivraison(updated.getDateLivraison());
        existing.setDateAnnulation(updated.getDateAnnulation());
        existing.setStatut(updated.getStatut());
        existing.setUtilisateur(updated.getUtilisateur());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
