-- ============================================================
-- GESTION DES UTILISATEURS ET PERMISSIONS
--
-- Trois rôles sont définis :
--   • admin   : compte de l'application Spring Boot
--                  (lecture/écriture sur toutes les tables métier)
--   • comptable  : accès en lecture aux vues financières uniquement
--   • marketing  : accès en lecture aux vues produits/clients
-- ============================================================

-- ------------------------------------------------------------
-- Création des utilisateurs MySQL
-- ------------------------------------------------------------
CREATE USER IF NOT EXISTS 'admin'@'%'  IDENTIFIED BY 'password';
CREATE USER IF NOT EXISTS 'comptable'@'%' IDENTIFIED BY 'comptable_password';
CREATE USER IF NOT EXISTS 'marketing'@'%' IDENTIFIED BY 'marketing_password';

-- ------------------------------------------------------------
-- Permissions : admin
-- Accès complet aux tables pour l'application Spring Boot.
-- ------------------------------------------------------------
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Utilisateur          TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Article               TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Vetement              TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Livre                 TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.JeuVideo              TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Electromenager        TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Commande              TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.LigneCommande         TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Avis                  TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Promotion             TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.UsagePromo            TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.DemandeRemboursement  TO 'admin'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON mydatabase.Remboursement         TO 'admin'@'%';

-- Accès en lecture aux vues
GRANT SELECT ON mydatabase.vue_ca_mensuel           TO 'admin'@'%';
GRANT SELECT ON mydatabase.vue_ca_annuel            TO 'admin'@'%';
GRANT SELECT ON mydatabase.vue_articles_populaires  TO 'admin'@'%';
GRANT SELECT ON mydatabase.vue_clients_actifs       TO 'admin'@'%';
GRANT SELECT ON mydatabase.vue_remboursements       TO 'admin'@'%';

-- ------------------------------------------------------------
-- Permissions : comptable
-- Lecture seule sur les vues financières et les remboursements.
-- Aucun accès direct aux tables (ni aux données personnelles).
-- ------------------------------------------------------------
GRANT SELECT ON mydatabase.vue_ca_mensuel     TO 'comptable'@'%';
GRANT SELECT ON mydatabase.vue_ca_annuel      TO 'comptable'@'%';
GRANT SELECT ON mydatabase.vue_remboursements TO 'comptable'@'%';

-- ------------------------------------------------------------
-- Permissions : marketing
-- Lecture seule sur les vues produits et clients, ainsi que
-- sur les tables Promotion et Avis.
-- Pas d'accès aux données financières ni aux mots de passe.
-- ------------------------------------------------------------
GRANT SELECT ON mydatabase.vue_articles_populaires TO 'marketing'@'%';
GRANT SELECT ON mydatabase.vue_clients_actifs      TO 'marketing'@'%';
GRANT SELECT ON mydatabase.Promotion               TO 'marketing'@'%';
GRANT SELECT ON mydatabase.Avis                    TO 'marketing'@'%';
GRANT SELECT ON mydatabase.Article                 TO 'marketing'@'%';

FLUSH PRIVILEGES;
