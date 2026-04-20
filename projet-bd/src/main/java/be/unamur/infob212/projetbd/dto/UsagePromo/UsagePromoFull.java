package be.unamur.infob212.projetbd.dto.UsagePromo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsagePromoFull {
    private Integer utilisateurId;
    private Integer promotionId;
    private Integer articleId;
    private Integer commandeId;
    private LocalDateTime dateUtilisation;
}
