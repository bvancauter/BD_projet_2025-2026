package be.unamur.infob212.projetbd.dto.Commande;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommandeList {
    private Integer id;
    private String statut;
    private LocalDateTime datePaiement;
    private Integer utilisateurId;
}
