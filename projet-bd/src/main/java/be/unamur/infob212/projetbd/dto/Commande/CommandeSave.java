package be.unamur.infob212.projetbd.dto.Commande;

import lombok.Data;

import java.util.List;

@Data
public class CommandeSave {
    private Integer utilisateurId;
    private List<LigneCommandeDto> lignes;
}
