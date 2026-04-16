-- ------------------------------------------------------------
-- Vue 1 : Chiffre d'affaires mensuel
-- Agrège les ventes par année et mois, en excluant les
-- commandes annulées. Utilisée par la comptabilité.
-- ------------------------------------------------------------
CREATE OR REPLACE VIEW vue_ca_mensuel AS
SELECT
    YEAR(c.date_paiement)  AS annee,
    MONTH(c.date_paiement) AS mois,
    COUNT(DISTINCT c.id)   AS nb_commandes,
    SUM(
        lc.quantite * a.prix * (1 - COALESCE(p.pourcentage, 0) / 100)
    ) AS chiffre_affaires
FROM Commande c
JOIN LigneCommande lc ON lc.commande_id = c.id
JOIN Article a        ON a.id = lc.article_id
LEFT JOIN UsagePromo up ON up.commande_id    = c.id
                       AND up.article_id     = lc.article_id
                       AND up.utilisateur_id = c.utilisateur_id
LEFT JOIN Promotion p   ON p.id = up.promotion_id
WHERE c.statut != 'ANNULEE'
GROUP BY YEAR(c.date_paiement), MONTH(c.date_paiement);

-- ------------------------------------------------------------
-- Vue 2 : Chiffre d'affaires annuel (basée sur vue_ca_mensuel)
-- Synthèse annuelle pour le rapport de comptabilité.
-- ------------------------------------------------------------
CREATE OR REPLACE VIEW vue_ca_annuel AS
SELECT
    annee,
    SUM(chiffre_affaires) AS chiffre_affaires,
    SUM(nb_commandes)     AS nb_commandes
FROM vue_ca_mensuel
GROUP BY annee;

-- ------------------------------------------------------------
-- Vue 3 : Articles populaires
-- Classe les articles par quantité vendue et note moyenne.
-- Utile pour le marketing.
-- ------------------------------------------------------------
CREATE OR REPLACE VIEW vue_articles_populaires AS
SELECT
    a.id,
    a.nom,
    a.prix,
    COALESCE(SUM(lc.quantite), 0)          AS quantite_vendue,
    COUNT(DISTINCT lc.commande_id)          AS nb_commandes,
    ROUND(AVG(av.note), 2)                  AS note_moyenne,
    COUNT(DISTINCT av.utilisateur_id)       AS nb_avis
FROM Article a
LEFT JOIN LigneCommande lc ON lc.article_id = a.id
LEFT JOIN Commande c       ON c.id = lc.commande_id AND c.statut != 'ANNULEE'
LEFT JOIN Avis av          ON av.article_id = a.id
GROUP BY a.id, a.nom, a.prix
ORDER BY quantite_vendue DESC;

-- ------------------------------------------------------------
-- Vue 4 : Clients actifs
-- Résume l'activité de chaque client : nombre de commandes,
-- total dépensé, date de dernière commande.
-- Accessible au marketing (pas aux données personnelles brutes).
-- ------------------------------------------------------------
CREATE OR REPLACE VIEW vue_clients_actifs AS
SELECT
    u.id,
    u.prenom,
    u.nom,
    u.email,
    COUNT(DISTINCT c.id)                    AS nb_commandes,
    COALESCE(SUM(lc.quantite * a.prix), 0)  AS total_depenses,
    MAX(c.date_paiement)                    AS derniere_commande
FROM Utilisateur u
LEFT JOIN Commande c       ON c.utilisateur_id = u.id AND c.statut != 'ANNULEE'
LEFT JOIN LigneCommande lc ON lc.commande_id = c.id
LEFT JOIN Article a        ON a.id = lc.article_id
GROUP BY u.id, u.prenom, u.nom, u.email;

-- ------------------------------------------------------------
-- Vue 5 : Remboursements en cours
-- Liste toutes les demandes de remboursement avec leur statut
-- (EN_ATTENTE ou REMBOURSE), le montant de la commande et les
-- informations du client. Réservée à la comptabilité.
-- ------------------------------------------------------------
CREATE OR REPLACE VIEW vue_remboursements AS
SELECT
    dr.id                                       AS demande_id,
    c.id                                        AS commande_id,
    u.prenom,
    u.nom,
    u.email,
    dr.date_demande,
    dr.raison,
    COALESCE(SUM(lc.quantite * a.prix), 0)      AS montant_commande,
    CASE
        WHEN r.id IS NOT NULL THEN 'REMBOURSE'
        ELSE 'EN_ATTENTE'
    END                                         AS statut_remboursement,
    r.date_remboursement
FROM DemandeRemboursement dr
JOIN Commande c        ON c.id = dr.commande_id
JOIN Utilisateur u     ON u.id = c.utilisateur_id
JOIN LigneCommande lc  ON lc.commande_id = c.id
JOIN Article a         ON a.id = lc.article_id
LEFT JOIN Remboursement r ON r.demande_remboursement_id = dr.id
GROUP BY dr.id, c.id, u.prenom, u.nom, u.email,
         dr.date_demande, dr.raison, r.id, r.date_remboursement;
