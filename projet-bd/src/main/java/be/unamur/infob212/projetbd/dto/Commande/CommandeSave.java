package be.unamur.infob212.projetbd.dto.Commande;

import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoSave;
import lombok.Data;

import java.util.List;

@Data
public class CommandeSave {
    private Integer utilisateurId;
    private List<LigneCommandeDto> lignes;
    private List<UsagePromoSave> promotions;
}
