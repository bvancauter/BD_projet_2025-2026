package be.unamur.infob212.projetbd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvisId implements Serializable {

    @Column(name = "utilisateur_id")
    private Integer utilisateurId;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "commande_id")
    private Integer commandeId;
}
