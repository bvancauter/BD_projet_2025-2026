package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.model.Utilisateur;
import be.unamur.infob212.projetbd.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getById(Integer id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + id));
    }

    public Utilisateur create(Utilisateur u) {
        return utilisateurRepository.save(u);
    }

    public Utilisateur update(Integer id, Utilisateur updated) {
        Utilisateur existing = getById(id);
        existing.setEmail(updated.getEmail());
        existing.setPrenom(updated.getPrenom());
        existing.setNom(updated.getNom());
        existing.setTelephone(updated.getTelephone());
        existing.setAdrRue(updated.getAdrRue());
        existing.setAdrNumero(updated.getAdrNumero());
        existing.setAdrVille(updated.getAdrVille());
        existing.setAdrCodePostal(updated.getAdrCodePostal());
        existing.setMotDePasse(updated.getMotDePasse());
        existing.setMethodePaiement(updated.getMethodePaiement());
        return utilisateurRepository.save(existing);
    }

    public void delete(Integer id) {
        utilisateurRepository.deleteById(id);
    }
}
