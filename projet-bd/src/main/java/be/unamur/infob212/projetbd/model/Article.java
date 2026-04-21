package be.unamur.infob212.projetbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private Double prix;
    private String type;
    private String auteur;
    private String isbn;
    @Enumerated(EnumType.STRING)
    private Taille taille;
    private String plateforme;
    private Integer pegi;
    private String marque;

    public enum Taille {
        XS,
        S,
        M,
        L,
        XL,
        XXL
    }
}