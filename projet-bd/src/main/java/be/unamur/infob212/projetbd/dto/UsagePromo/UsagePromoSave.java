package be.unamur.infob212.projetbd.dto.UsagePromo;

import lombok.Data;

@Data
public class UsagePromoSave {
    private Integer utilisateurId;
    private Integer promotionId;
    private Integer articleId;
    private Integer commandeId;
}
