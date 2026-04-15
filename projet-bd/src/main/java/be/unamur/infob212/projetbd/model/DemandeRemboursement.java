package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "DemandeRemboursement")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRemboursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demandeRemboursementId;

    @OneToOne
    @JoinColumn(name = "commandId", unique = true)
    private Commande commande;

    private Date date;
    private String raison;
}
