package be.unamur.infob212.projetbd.dto.Rapport;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClientActifDTO {
    private Integer id;
    private String prenom;
    private String nom;
    private String email;
    private Long nbCommandes;
    private Double totalDepenses;
    private LocalDateTime derniereCommande;
}
