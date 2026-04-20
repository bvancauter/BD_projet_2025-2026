package be.unamur.infob212.projetbd.dto.Promotion;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromotionFull {
    private Integer id;
    private String nom;
    private String description;
    private Double pourcentage;
    private LocalDate dateDebut;
    private LocalDate dateFin;
}
