-- Création des tables
CREATE TABLE Utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    adr_rue VARCHAR(255) NOT NULL,
    adr_numero VARCHAR(10) NOT NULL,
    adr_ville VARCHAR(100) NOT NULL,
    adr_code_postal VARCHAR(20) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    methode_paiement VARCHAR(100) NOT NULL
);

CREATE TABLE Article (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    prix DECIMAL(10,2) NOT NULL CHECK (prix > 0)
);

CREATE TABLE Vetement (
    id INT PRIMARY KEY,
    taille ENUM('XS', 'S', 'M', 'L', 'XL', 'XXL') NOT NULL,
    FOREIGN KEY (id) REFERENCES Article(id) ON DELETE CASCADE
);

CREATE TABLE Livre (
    id INT PRIMARY KEY,
    auteur VARCHAR(255) NOT NULL,
    isbn VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (id) REFERENCES Article(id) ON DELETE CASCADE
);

CREATE TABLE JeuVideo (
    id INT PRIMARY KEY,
    plateforme VARCHAR(100) NOT NULL,
    pegi INT NOT NULL CHECK (pegi IN (3, 7, 12, 16, 18)),
    FOREIGN KEY (id) REFERENCES Article(id) ON DELETE CASCADE
);

CREATE TABLE Electromenager (
    id INT PRIMARY KEY,
    marque VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES Article(id) ON DELETE CASCADE
);

CREATE TABLE Commande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_paiement DATETIME NOT NULL,
    date_livraison DATETIME,
    date_annulation DATETIME,
    statut ENUM('EN_ATTENTE','EXPEDIEE','LIVREE','ANNULEE') NOT NULL,
    utilisateur_id INT NOT NULL,
    CHECK (
        date_livraison IS NULL OR date_livraison >= date_paiement
    ),
    CHECK (
        date_annulation IS NULL OR date_annulation >= date_paiement
    ),
    CHECK (
        NOT (date_livraison IS NOT NULL AND date_annulation IS NOT NULL)
    ),
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE LigneCommande (
    commande_id INT,
    article_id INT,
    quantite INT NOT NULL CHECK (quantite > 0),
    PRIMARY KEY (commande_id, article_id),
    FOREIGN KEY (commande_id) REFERENCES Commande(id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES Article(id)
);

CREATE TABLE Avis (
    utilisateur_id INT,
    article_id INT,
    commande_id INT,
    note INT NOT NULL CHECK (note BETWEEN 1 AND 5),
    commentaire TEXT NOT NULL,
    date_avis DATETIME NOT NULL,
    PRIMARY KEY (utilisateur_id, article_id, commande_id),
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES Article(id) ON DELETE CASCADE,
    FOREIGN KEY (commande_id) REFERENCES Commande(id) ON DELETE CASCADE
);

CREATE TABLE Promotion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    pourcentage DECIMAL(5,2) NOT NULL CHECK (pourcentage BETWEEN 0 AND 100),
    date_debut DATE,
    date_fin DATE,
    CHECK (
        date_fin IS NULL OR date_fin >= date_debut
    )
);

CREATE TABLE UsagePromo (
    utilisateur_id INT,
    promotion_id INT,
    article_id INT,
    date_utilisation DATETIME NOT NULL,
    PRIMARY KEY (utilisateur_id, promotion_id, article_id),
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (promotion_id) REFERENCES Promotion(id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES Article(id) ON DELETE CASCADE
);

CREATE TABLE DemandeRemboursement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    commande_id INT NOT NULL,
    date_demande DATETIME NOT NULL,
    raison TEXT NOT NULL,
    FOREIGN KEY (commande_id) REFERENCES Commande(id) ON DELETE CASCADE
);

CREATE TABLE Remboursement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    demande_remboursement_id INT NOT NULL,
    date_remboursement DATETIME NOT NULL,
    FOREIGN KEY (demande_remboursement_id) REFERENCES DemandeRemboursement(id) ON DELETE CASCADE
);

-- Index
CREATE INDEX idx_commande_utilisateur ON Commande(utilisateur_id);

CREATE INDEX idx_lignecommande_article ON LigneCommande(article_id);

CREATE INDEX idx_avis_utilisateur ON Avis(utilisateur_id);
CREATE INDEX idx_avis_article ON Avis(article_id);

CREATE INDEX idx_article_nom ON Article(nom);
CREATE INDEX idx_article_prix ON Article(prix);

CREATE INDEX idx_commande_datepaiement ON Commande(date_paiement);
CREATE INDEX idx_commande_datelivraison ON Commande(date_livraison);


-- Populate
INSERT INTO Utilisateur (email, prenom, nom, telephone, adr_rue, adr_numero, adr_ville, adr_code_postal, mot_de_passe, methode_paiement)
VALUES
('alice@example.com', 'Alice', 'Dupont', '0601020304', 'Rue des Lilas', '12', 'Paris', '75001', 'mdpAlice123', 'Carte bancaire'),
('bob@example.com', 'Bob', 'Martin', '0605060708', 'Avenue des Champs', '34', 'Lyon', '69001', 'mdpBob123', 'Paypal'),
('carol@example.com', 'Carol', 'Durand', '0611121314', 'Boulevard Saint-Germain', '56', 'Paris', '75005', 'mdpCarol123', 'Carte bancaire'),
('dave@example.com', 'Dave', 'Leroy', '0615161718', 'Rue de la Paix', '78', 'Marseille', '13001', 'mdpDave123', 'Carte bancaire'),
('eve@example.com', 'Eve', 'Moreau', '0619202122', 'Place Bellecour', '90', 'Lyon', '69002', 'mdpEve123', 'Paypal');

INSERT INTO Article (nom, description, prix) VALUES
('T-shirt Rouge', 'T-shirt 100% coton, rouge', 19.99),
('Jean Bleu', 'Jean slim bleu', 49.99),
('Le Petit Prince', 'Livre classique de Saint-Exupéry', 12.50),
('Harry Potter Tome 1', 'Livre de J.K. Rowling', 15.00),
('Shenmue', 'Jeu d\'action-aventure', 59.99),
('Resident Evil', 'Jeu de surivial horror', 69.99),
('Mixeur Philips', 'Mixeur 800W', 89.99),
('Aspirateur Dyson', 'Aspirateur sans fil', 299.99);

INSERT INTO Vetement (id, taille) VALUES
(1, 'M'),
(2, 'L');

INSERT INTO Livre (id, auteur, isbn) VALUES
(3, 'Antoine de Saint-Exupéry', '978-0156012195'),
(4, 'J.K. Rowling', '978-0747532699');

INSERT INTO JeuVideo (id, plateforme, pegi) VALUES
(5, 'Dreamcast', 12),
(6, 'PC', 18);

INSERT INTO Electromenager (id, marque) VALUES
(7, 'Philips'),
(8, 'Dyson');

INSERT INTO Commande (date_paiement, date_livraison, statut, utilisateur_id) VALUES
('2026-03-01 10:00:00', '2026-03-03 14:00:00', 'LIVREE', 1),
('2026-03-05 15:30:00', NULL, 'EN_ATTENTE', 1),
('2026-03-07 12:00:00', '2026-03-09 16:00:00', 'EXPEDIEE', 2),
('2026-03-08 11:20:00', '2026-03-10 17:00:00', 'LIVREE', 2),
('2026-03-09 09:00:00', NULL, 'ANNULEE', 3);

INSERT INTO LigneCommande (commande_id, article_id, quantite) VALUES
(1, 1, 2),
(1, 3, 1),
(2, 2, 1),
(3, 5, 1),
(3, 7, 1),
(4, 4, 2),
(4, 6, 1);

INSERT INTO Avis (utilisateur_id, article_id, commande_id, note, commentaire, date_avis) VALUES
(1, 1, 1, 5, 'Super T-shirt !', '2026-03-04 10:00:00'),
(2, 5, 3, 4, 'Bon jeu, mais un peu court.', '2026-03-10 12:00:00'),
(2, 4, 4, 5, 'Livre passionnant !', '2026-03-11 09:00:00');

INSERT INTO Promotion (nom, description, pourcentage, date_debut, date_fin) VALUES
('Promo Printemps', '10% sur tous les articles', 10.00, '2026-04-01', '2026-04-30'),
('Soldes Été', '20% sur les vêtements', 20.00, '2026-06-01', '2026-06-30');

INSERT INTO UsagePromo (utilisateur_id, promotion_id, article_id, date_utilisation) VALUES
(1, 1, 1, '2026-04-02 12:00:00'),
(2, 2, 2, '2026-06-05 14:00:00');

INSERT INTO DemandeRemboursement (commande_id, date_demande, raison) VALUES
(5, '2026-03-10 09:30:00', 'Produit défectueux');
INSERT INTO Remboursement (demande_remboursement_id, date_remboursement) VALUES
(1, '2026-03-15 11:00:00');
