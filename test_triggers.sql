-- ============================================================
-- TEST DES TRIGGERS
-- ============================================================

-- ============================================================
-- TRIGGER 1 : check_avis_valid
-- ============================================================

-- Test (KO) : commande n'appartient pas à l'utilisateur
INSERT INTO Avis VALUES (3, 1, 1, 4, 'Test', NOW());

-- Test (KO) : article pas dans la commande
INSERT INTO Avis VALUES (1, 7, 1, 4, 'Test', NOW());

-- Test valide (OK) :
INSERT INTO Avis VALUES (1, 3, 1, 5, 'Très bien', NOW());


-- ============================================================
-- TRIGGER 2 : check_usagepromo_dates
-- ============================================================

-- Test (KO) : date hors période
INSERT INTO UsagePromo VALUES (1, 1, 1, '2026-05-10 10:00:00');

-- Test valide (OK) :
INSERT INTO UsagePromo VALUES (1, 1, 1, '2026-04-10 10:00:00');


-- ============================================================
-- TRIGGER 3 : check_demande_remboursement
-- ============================================================

-- Test (KO) : commande pas livrée/annulée (commande 2 = En attente)
INSERT INTO DemandeRemboursement (commande_id, date_demande, raison)
VALUES (2, NOW(), 'Test');

-- Test valide (OK) : (commande 5 = Annulée)
INSERT INTO DemandeRemboursement (commande_id, date_demande, raison)
VALUES (5, NOW(), 'Test OK');


-- ============================================================
-- TRIGGER 4 : check_remboursement_unique
-- ============================================================

-- Test (KO) : remboursement déjà existant
INSERT INTO Remboursement (demande_remboursement_id, date_remboursement)
VALUES (1, NOW());


-- ============================================================
-- TRIGGER 5 : check_lignecommande_commande_active
-- ============================================================

-- Test (KO) : commande annulée (commande 5)
INSERT INTO LigneCommande VALUES (5, 1, 1);


-- ============================================================
-- TRIGGER 6 : check_update_commande
-- ============================================================

-- Test (KO) : modification d'une commande livrée (commande 1)
UPDATE Commande SET statut = 'En attente' WHERE id = 1;


-- ============================================================
-- TRIGGER 7 : check_avis_apres_livraison
-- ============================================================

-- Test (KO) : avis avant livraison (commande 2 = En attente)
INSERT INTO Avis VALUES (2, 1, 1, 4, 'Test avant livraison', NOW());

-- Test valide (OK) : avis après livraison (commande 1 = Livrée)
INSERT INTO Avis VALUES (1, 1, 1, 5, 'Avis après livraison', NOW());


-- ============================================================
-- TRIGGER 8 : check_demande_remboursement_date
-- ============================================================

-- Test (KO) : demande de remboursement avant la date de livraison
-- (commande 1 livrée le 2026-03-10, on met une date avant)
INSERT INTO DemandeRemboursement (commande_id, date_demande, raison)
VALUES (1, '2026-03-01 10:00:00', 'Test avant livraison');

-- Test valide (OK) : demande après la date de livraison
INSERT INTO DemandeRemboursement (commande_id, date_demande, raison)
VALUES (1, '2026-03-15 10:00:00', 'Test après livraison');


-- ============================================================
-- TRIGGER 9 : check_usagepromo_debut
-- ============================================================

-- Test (KO) : utilisation avant date_debut
-- (promotion 1 commence le 2026-04-01, on met une date avant)
INSERT INTO UsagePromo VALUES (1, 1, 1, '2026-03-20 10:00:00');

-- Test valide (OK) : utilisation après date_debut
INSERT INTO UsagePromo VALUES (1, 1, 1, '2026-04-05 10:00:00');


-- ============================================================
-- TRIGGER 10 : check_article_vetement
-- ============================================================

-- Test (KO) : article déjà un Livre (id = 3)
INSERT INTO Vetement VALUES (3, 'M');

-- Test (KO) : article déjà un Jeu Vidéo (id = 5)
INSERT INTO Vetement VALUES (5, 'L');

-- Test (KO) : article déjà un Électroménager (id = 7)
INSERT INTO Vetement VALUES (7, 'XL');

-- Test (OK) : article non spécialisé (id = 8 → Dyson)
INSERT INTO Vetement VALUES (8, 'S');


-- ============================================================
-- TRIGGER 11 : check_article_livre
-- ============================================================

-- Test (KO) : article déjà un Vêtement (id = 1)
INSERT INTO Livre VALUES (1, 'Auteur Test', 'ISBN-TEST-1');

-- Test (KO) : article déjà un Jeu Vidéo (id = 6)
INSERT INTO Livre VALUES (6, 'Auteur Test', 'ISBN-TEST-2');

-- Test (KO) : article déjà un Électroménager (id = 7)
INSERT INTO Livre VALUES (7, 'Auteur Test', 'ISBN-TEST-3');

-- Test (OK) : article non spécialisé (id = 2 → Jean Bleu)
INSERT INTO Livre VALUES (2, 'Auteur Test', 'ISBN-TEST-4');


-- ============================================================
-- TRIGGER 12 : check_article_jeu
-- ============================================================

-- Test (KO) : article déjà un Vêtement (id = 2)
INSERT INTO JeuVideo VALUES (2, 'PC', 12);

-- Test (KO) : article déjà un Livre (id = 4)
INSERT INTO JeuVideo VALUES (4, 'Switch', 7);

-- Test (KO) : article déjà un Électroménager (id = 8)
INSERT INTO JeuVideo VALUES (8, 'PS5', 18);

-- Test (OK) : article non spécialisé (id = 1 → T-shirt Rouge)
INSERT INTO JeuVideo VALUES (1, 'Xbox', 16);


-- ============================================================
-- TRIGGER 13 : check_article_electro
-- ============================================================

-- Test (KO) : article déjà un Vêtement (id = 1)
INSERT INTO Electromenager VALUES (1, 'TestMarque');

-- Test (KO) : article déjà un Livre (id = 3)
INSERT INTO Electromenager VALUES (3, 'TestMarque');

-- Test (KO) : article déjà un Jeu Vidéo (id = 5)
INSERT INTO Electromenager VALUES (5, 'TestMarque');

-- Test (OK) : article non spécialisé (id = 2 → Jean Bleu)
INSERT INTO Electromenager VALUES (2, 'TestMarque');
