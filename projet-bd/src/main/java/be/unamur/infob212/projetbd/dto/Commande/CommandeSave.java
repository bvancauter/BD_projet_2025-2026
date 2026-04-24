package be.unamur.infob212.projetbd.dto.Commande;

import be.unamur.infob212.projetbd.dto.UsagePromo.UsagePromoSave;
import lombok.Data;

import java.util.List;

@Data
public class CommandeSave {
    private List<LigneCommandeSave> lignes;
    private List<UsagePromoSave> promotions;
}
