package be.unamur.infob212.projetbd.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Avis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avis {

    @EmbeddedId
    private AvisId id;

    private Integer note;

    private String commentaire;

    @Column(name = "date_avis")
    private LocalDateTime dateAvis;
}
