package be.unamur.infob212.projetbd.dto.Promotion;

import lombok.Data;

@Data
public class PromotionList {
    private Integer id;
    private String nom;
    private Double pourcentage;
}
