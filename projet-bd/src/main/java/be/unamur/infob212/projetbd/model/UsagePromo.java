package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "UsagePromo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsagePromo {

    @EmbeddedId
    private UsagePromoId id;

    @Column(name = "date_utilisation")
    private LocalDateTime dateUtilisation;
}
