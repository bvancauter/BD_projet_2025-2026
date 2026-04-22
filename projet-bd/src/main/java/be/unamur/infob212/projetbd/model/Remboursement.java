package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Remboursement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remboursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "demande_remboursement_id")
    private Integer demandeRemboursementId;

    @Column(name = "date_remboursement")
    private LocalDateTime dateRemboursement;
}
