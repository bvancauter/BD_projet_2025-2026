package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "Remboursement")
@NoArgsConstructor
@AllArgsConstructor
public class Remboursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer remboursementId;

    @OneToOne
    @JoinColumn(name = "demandeRemboursementId", unique = true)
    private DemandeRemboursement demandeRemboursement;

    private Date date;
}
