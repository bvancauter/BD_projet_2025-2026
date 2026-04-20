-- ============================================================
-- GESTION DES UTILISATEURS ET PERMISSIONS
-- 3 rôles demandés :
--   CLIENT
--   COMPTABLE
--   MARKETING
--
-- Compatible avec :
-- Utilisateur.role = CLIENT / COMPTABLE / MARKETING
-- ============================================================

-- ------------------------------------------------------------
-- Création des rôles MySQL
-- ------------------------------------------------------------
CREATE ROLE IF NOT EXISTS 'client';
CREATE ROLE IF NOT EXISTS 'comptable';
CREATE ROLE IF NOT EXISTS 'marketing';

-- ------------------------------------------------------------
-- Création des comptes MySQL
-- ------------------------------------------------------------
CREATE USER IF NOT EXISTS 'client_user'@'%'      IDENTIFIED BY 'client_password';
CREATE USER IF NOT EXISTS 'comptable_user'@'%'   IDENTIFIED BY 'comptable_password';
CREATE USER IF NOT EXISTS 'marketing_user'@'%'   IDENTIFIED BY 'marketing_password';

-- ============================================================
-- ROLE CLIENT
-- Utilisateur normal du site e-commerce
-- ============================================================

GRANT SELECT, INSERT, UPDATE ON mydatabase.Utilisateur   TO 'client';

GRANT SELECT ON mydatabase.Article         TO 'client';
GRANT SELECT ON mydatabase.Promotion       TO 'client';
GRANT SELECT ON mydatabase.Avis            TO 'client';

GRANT SELECT, INSERT ON mydatabase.Commande             TO 'client';
GRANT SELECT, INSERT ON mydatabase.LigneCommande        TO 'client';
GRANT SELECT, INSERT ON mydatabase.DemandeRemboursement TO 'client';

-- ============================================================
-- ROLE COMPTABLE
-- Accès financier uniquement
-- ============================================================

GRANT SELECT ON mydatabase.vue_ca_mensuel      TO 'comptable';
GRANT SELECT ON mydatabase.vue_ca_annuel       TO 'comptable';
GRANT SELECT ON mydatabase.vue_remboursements  TO 'comptable';
GRANT SELECT ON mydatabase.Remboursement       TO 'comptable';

-- ============================================================
-- ROLE MARKETING
-- Analyse produits / clients
-- ============================================================

GRANT SELECT ON mydatabase.vue_articles_populaires TO 'marketing';
GRANT SELECT ON mydatabase.vue_clients_actifs      TO 'marketing';
GRANT SELECT ON mydatabase.Article                 TO 'marketing';
GRANT SELECT ON mydatabase.Avis                    TO 'marketing';
GRANT SELECT ON mydatabase.Promotion               TO 'marketing';

-- ------------------------------------------------------------
-- Attribution des rôles
-- ------------------------------------------------------------
GRANT 'client'     TO 'client_user'@'%';
GRANT 'comptable' TO 'comptable_user'@'%';
GRANT 'marketing' TO 'marketing_user'@'%';

SET DEFAULT ROLE 'client'     TO 'client_user'@'%';
SET DEFAULT ROLE 'comptable' TO 'comptable_user'@'%';
SET DEFAULT ROLE 'marketing' TO 'marketing_user'@'%';

FLUSH PRIVILEGES;
