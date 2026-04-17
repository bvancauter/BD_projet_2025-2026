package be.unamur.infob212.projetbd.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LigneCommande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneCommande {

    @EmbeddedId
    private LigneCommandeId id;

    private Integer quantite;
}
