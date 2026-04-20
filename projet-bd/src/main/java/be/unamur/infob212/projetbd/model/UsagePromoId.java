package be.unamur.infob212.projetbd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UsagePromoId implements Serializable {

    @Column(name = "utilisateur_id")
    private Integer utilisateurId;

    @Column(name = "promotion_id")
    private Integer promotionId;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "commande_id")
    private Integer commandeId;
}
