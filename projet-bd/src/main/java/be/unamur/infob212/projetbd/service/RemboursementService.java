package be.unamur.infob212.projetbd.service;

import be.unamur.infob212.projetbd.dto.Remboursement.DemandeRemboursementFull;
import be.unamur.infob212.projetbd.dto.Remboursement.DemandeRemboursementSave;
import be.unamur.infob212.projetbd.dto.Remboursement.RemboursementFull;
import be.unamur.infob212.projetbd.model.DemandeRemboursement;
import be.unamur.infob212.projetbd.model.Remboursement;
import be.unamur.infob212.projetbd.repository.CommandeRepository;
import be.unamur.infob212.projetbd.repository.DemandeRemboursementRepository;
import be.unamur.infob212.projetbd.repository.RemboursementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RemboursementService {

    private final DemandeRemboursementRepository demandeRepository;
    private final RemboursementRepository remboursementRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeService commandeService;

    public List<DemandeRemboursementFull> getAllDemandes() {
        return demandeRepository.findAll()
                .stream()
                .map(this::toDemandeFull)
                .toList();
    }

    public List<DemandeRemboursementFull> getDemandesByCommande(Integer commandeId) {
        return demandeRepository.findByCommandeId(commandeId)
                .stream()
                .map(this::toDemandeFull)
                .toList();
    }

    public DemandeRemboursementFull createDemande(DemandeRemboursementSave dto) {

        if (commandeRepository.findById(dto.getCommandeId()).isEmpty()) {
            throw new RuntimeException("COMMANDE_NOT_FOUND");
        }

        DemandeRemboursement demande = new DemandeRemboursement();
        demande.setCommandeId(dto.getCommandeId());
        demande.setDateDemande(LocalDateTime.now());
        demande.setRaison(dto.getRaison());

        // La vérification que la commande est LIVREE ou ANNULEE
        // est gérée par le trigger check_demande_remboursement en base
        return toDemandeFull(demandeRepository.save(demande));
    }

    public RemboursementFull validerRemboursement(Integer demandeId) {

        if (demandeRepository.findById(demandeId).isEmpty()) {
            throw new RuntimeException("DEMANDE_NOT_FOUND");
        }

        if (remboursementRepository.findByDemandeRemboursementId(demandeId).isPresent()) {
            throw new RuntimeException("REMBOURSEMENT_ALREADY_EXISTS");
        }

        Remboursement remboursement = new Remboursement();
        remboursement.setDemandeRemboursementId(demandeId);
        remboursement.setDateRemboursement(LocalDateTime.now());

        return toRemboursementFull(remboursementRepository.save(remboursement));
    }

    private DemandeRemboursementFull toDemandeFull(DemandeRemboursement demande) {
        DemandeRemboursementFull dto = new DemandeRemboursementFull();
        dto.setId(demande.getId());
        dto.setDateDemande(demande.getDateDemande());
        dto.setRaison(demande.getRaison());

        commandeService.get(demande.getCommandeId())
                .ifPresent(dto::setCommande);

        remboursementRepository.findByDemandeRemboursementId(demande.getId())
                .ifPresent(r -> dto.setRemboursement(toRemboursementFull(r)));

        return dto;
    }

    private RemboursementFull toRemboursementFull(Remboursement remboursement) {
        RemboursementFull dto = new RemboursementFull();
        dto.setId(remboursement.getId());
        dto.setDemandeRemboursementId(remboursement.getDemandeRemboursementId());
        dto.setDateRemboursement(remboursement.getDateRemboursement());
        return dto;
    }
}
