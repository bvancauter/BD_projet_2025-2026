DELIMITER $$

/* ============================================================
   TRIGGER 1 : Un utilisateur ne peut laisser un avis que s’il
               a acheté l’article dans la commande
   ============================================================ */
CREATE TRIGGER check_avis_valid
BEFORE INSERT ON Avis
FOR EACH ROW
BEGIN
    -- Vérifier que la commande appartient à l'utilisateur
    IF NOT EXISTS (
        SELECT 1
        FROM Commande c
        WHERE c.id = NEW.commande_id
          AND c.utilisateur_id = NEW.utilisateur_id
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible de laisser un avis : la commande n appartient pas à cet utilisateur.';
    END IF;

    -- Vérifier que la commande contient l article
    IF NOT EXISTS (
        SELECT 1
        FROM LigneCommande lc
        WHERE lc.commande_id = NEW.commande_id
          AND lc.article_id = NEW.article_id
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible de laisser un avis : l article n a pas été acheté dans cette commande.';
    END IF;
END$$


/* ============================================================
   TRIGGER 2 : Une promotion ne peut être utilisée que pendant
               sa période de validité
   ============================================================ */
CREATE TRIGGER check_usagepromo_dates
BEFORE INSERT ON UsagePromo
FOR EACH ROW
BEGIN
    DECLARE d_debut DATE;
    DECLARE d_fin DATE;

    SELECT date_debut, date_fin
    INTO d_debut, d_fin
    FROM Promotion
    WHERE id = NEW.promotion_id;

    IF NEW.date_utilisation < d_debut
       OR (d_fin IS NOT NULL AND NEW.date_utilisation > d_fin) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Promotion non valide à cette date.';
    END IF;
END$$


/* ============================================================
   TRIGGER 3 : Une demande de remboursement ne peut être créée
               que si la commande est livrée ou annulée
   ============================================================ */
CREATE TRIGGER check_demande_remboursement
BEFORE INSERT ON DemandeRemboursement
FOR EACH ROW
BEGIN
    DECLARE st VARCHAR(20);

    SELECT statut INTO st
    FROM Commande
    WHERE id = NEW.commande_id;

    IF st NOT IN ('LIVREE', 'ANNULEE') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Remboursement impossible : la commande doit être livrée ou annulée.';
    END IF;
END$$


/* ============================================================
   TRIGGER 4 : Une demande ne peut être remboursée qu’une seule fois
   ============================================================ */
CREATE TRIGGER check_remboursement_unique
BEFORE INSERT ON Remboursement
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Remboursement r
        WHERE r.demande_remboursement_id = NEW.demande_remboursement_id
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cette demande a déjà un remboursement.';
    END IF;
END$$


/* ============================================================
   TRIGGER 5 : Interdire d’ajouter une ligne de commande si la
               commande est annulée
   ============================================================ */
CREATE TRIGGER check_lignecommande_commande_active
BEFORE INSERT ON LigneCommande
FOR EACH ROW
BEGIN
    DECLARE st VARCHAR(20);

    SELECT statut INTO st
    FROM Commande
    WHERE id = NEW.commande_id;

    IF st = 'ANNULEE' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible d ajouter un article : la commande est annulée.';
    END IF;
END$$


/* ============================================================
   TRIGGER 6 : Interdire de modifier une commande livrée ou annulée
   ============================================================ */
CREATE TRIGGER check_update_commande
BEFORE UPDATE ON Commande
FOR EACH ROW
BEGIN
    IF OLD.statut IN ('LIVREE', 'ANNULEE') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible de modifier une commande livrée ou annulée.';
    END IF;
END$$


/* ============================================================
   TRIGGER 7 : Interdire un avis avant la livraison de la commande
   ============================================================ */
CREATE TRIGGER check_avis_apres_livraison
BEFORE INSERT ON Avis
FOR EACH ROW
BEGIN
    DECLARE st VARCHAR(20);

    SELECT statut INTO st
    FROM Commande
    WHERE id = NEW.commande_id;

    IF st <> 'LIVREE' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible de laisser un avis : la commande n est pas encore livrée.';
    END IF;
END$$


/* ============================================================
   TRIGGER 8 : Interdire une demande de remboursement avant la date de livraison
   ============================================================ */
CREATE TRIGGER check_demande_remboursement_date
BEFORE INSERT ON DemandeRemboursement
FOR EACH ROW
BEGIN
    DECLARE d_liv DATETIME;

    SELECT date_livraison INTO d_liv
    FROM Commande
    WHERE id = NEW.commande_id;

    -- Si la commande est livrée, la demande doit être après la livraison
    IF d_liv IS NOT NULL AND NEW.date_demande < d_liv THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La demande de remboursement doit être postérieure à la date de livraison.';
    END IF;
END$$


/* ============================================================
   TRIGGER 9 : Interdire l’utilisation d’une promotion avant sa date de début
   ============================================================ */
CREATE TRIGGER check_usagepromo_debut
BEFORE INSERT ON UsagePromo
FOR EACH ROW
BEGIN
    DECLARE d_debut DATE;

    SELECT date_debut INTO d_debut
    FROM Promotion
    WHERE id = NEW.promotion_id;

    IF NEW.date_utilisation < d_debut THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible d utiliser la promotion avant sa date de début.';
    END IF;
END$$


/* ============================================================
   TRIGGER 10 : Empêcher qu’un article déjà spécialisé soit inséré dans Vetement
   ============================================================ */
CREATE TRIGGER check_article_vetement
BEFORE INSERT ON Vetement
FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM Livre WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Livre.';
    END IF;

    IF EXISTS (SELECT 1 FROM JeuVideo WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Jeu Vidéo.';
    END IF;

    IF EXISTS (SELECT 1 FROM Electromenager WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Électroménager.';
    END IF;
END$$


/* ============================================================
   TRIGGER 11 : Empêcher qu’un article déjà spécialisé soit inséré dans Livre
   ============================================================ */
CREATE TRIGGER check_article_livre
BEFORE INSERT ON Livre
FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM Vetement WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Vêtement.';
    END IF;

    IF EXISTS (SELECT 1 FROM JeuVideo WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Jeu Vidéo.';
    END IF;

    IF EXISTS (SELECT 1 FROM Electromenager WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Électroménager.';
    END IF;
END$$


/* ============================================================
   TRIGGER 12 : Empêcher qu’un article déjà spécialisé soit inséré dans JeuVideo
   ============================================================ */
CREATE TRIGGER check_article_jeu
BEFORE INSERT ON JeuVideo
FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM Vetement WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Vêtement.';
    END IF;

    IF EXISTS (SELECT 1 FROM Livre WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Livre.';
    END IF;

    IF EXISTS (SELECT 1 FROM Electromenager WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Électroménager.';
    END IF;
END$$


/* ============================================================
   TRIGGER 13 : Empêcher qu’un article déjà spécialisé soit inséré dans Electromenager
   ============================================================ */
CREATE TRIGGER check_article_electro
BEFORE INSERT ON Electromenager
FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM Vetement WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Vêtement.';
    END IF;

    IF EXISTS (SELECT 1 FROM Livre WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Livre.';
    END IF;

    IF EXISTS (SELECT 1 FROM JeuVideo WHERE id = NEW.id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Impossible : cet article est déjà un Jeu Vidéo.';
    END IF;
END$$


DELIMITER ;
