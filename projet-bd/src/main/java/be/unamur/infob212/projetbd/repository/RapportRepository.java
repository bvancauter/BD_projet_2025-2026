package be.unamur.infob212.projetbd.repository;

import be.unamur.infob212.projetbd.dto.Rapport.ArticlePopulaireDTO;
import be.unamur.infob212.projetbd.dto.Rapport.ClientActifDTO;
import be.unamur.infob212.projetbd.dto.Rapport.VueRemboursementDTO;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RapportRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ArticlePopulaireDTO> getArticlesPopulaires() {
        String sql = "SELECT * FROM vue_articles_populaires";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ArticlePopulaireDTO(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getLong("quantite_vendue"),
                        rs.getLong("nb_commandes"),
                        rs.getDouble("note_moyenne"),
                        rs.getLong("nb_avis")
                )
        );
    }

    public List<ClientActifDTO> getClientsActifs() {
        String sql = "SELECT * FROM vue_clients_actifs ORDER BY nb_commandes DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClientActifDTO(
                        rs.getInt("id"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getLong("nb_commandes"),
                        rs.getDouble("total_depenses"),
                        rs.getTimestamp("derniere_commande") != null
                                ? rs.getTimestamp("derniere_commande").toLocalDateTime()
                                : null
                )
        );
    }

    public List<VueRemboursementDTO> getRemboursements() {
        String sql = "SELECT * FROM vue_remboursements ORDER BY date_demande DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new VueRemboursementDTO(
                        rs.getInt("demande_id"),
                        rs.getInt("commande_id"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getTimestamp("date_demande").toLocalDateTime(),
                        rs.getString("raison"),
                        rs.getDouble("montant_commande"),
                        rs.getString("statut_remboursement"),
                        rs.getTimestamp("date_remboursement") != null
                                ? rs.getTimestamp("date_remboursement").toLocalDateTime()
                                : null
                )
        );
    }
}
