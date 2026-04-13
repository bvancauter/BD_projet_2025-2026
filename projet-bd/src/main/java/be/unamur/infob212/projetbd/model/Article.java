package be.unamur.infob212.projetbd.model;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    private String description;

    private double prix;
}