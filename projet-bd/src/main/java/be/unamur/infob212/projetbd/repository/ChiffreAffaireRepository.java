package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.dto.ChiffreAffaire.CaAnnuelDTO;
import be.unamur.infob212.projetbd.dto.ChiffreAffaire.CaMensuelDTO;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ChiffreAffaireRepository {
    private final JdbcTemplate jdbcTemplate;


    public List<CaMensuelDTO> getCaMensuel() {

        String sql = "SELECT * FROM vue_ca_mensuel ORDER BY annee, mois";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CaMensuelDTO(
                        rs.getInt("annee"),
                        rs.getInt("mois"),
                        rs.getLong("nb_commandes"),
                        rs.getDouble("chiffre_affaires")
                )
        );
    }

    public List<CaAnnuelDTO> getCaAnnuel() {

        String sql = "SELECT * FROM vue_ca_annuel ORDER BY annee";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CaAnnuelDTO(
                        rs.getInt("annee"),
                        rs.getDouble("chiffre_affaires"),
                        rs.getLong("nb_commandes")
                )
        );
    }
}
