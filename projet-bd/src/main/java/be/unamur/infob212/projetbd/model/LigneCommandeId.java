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
public class LigneCommandeId implements Serializable {

    @Column(name = "commande_id")
    private Integer commandeId;

    @Column(name = "article_id")
    private Integer articleId;
}
