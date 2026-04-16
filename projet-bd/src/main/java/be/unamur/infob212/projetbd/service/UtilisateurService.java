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
        u.setRole(Utilisateur.Role.CLIENT);
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

    /**
     * Désinscription RGPD : anonymise toutes les données personnelles du compte
     * tout en conservant l'historique des commandes et des avis.
     * Après cette opération, il n'est plus possible d'identifier le client
     * à partir des données restantes.
     */
    public void anonymize(Integer id) {
        Utilisateur existing = getById(id);
        existing.setEmail("anonyme_" + id + "@supprime.invalid");
        existing.setPrenom("Anonyme");
        existing.setNom("Anonyme");
        existing.setTelephone(null);
        existing.setAdrRue("Anonyme");
        existing.setAdrNumero("0");
        existing.setAdrVille("Anonyme");
        existing.setAdrCodePostal("00000");
        existing.setMotDePasse("SUPPRIME");
        existing.setMethodePaiement("SUPPRIME");
        utilisateurRepository.save(existing);
    }
}
