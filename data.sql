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
    taille VARCHAR(5) NOT NULL CHECK (taille IN ('XS', 'S', 'M', 'L', 'XL', 'XXL')),
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
    statut VARCHAR(20) NOT NULL CHECK (statut IN ('En attente','Expédiée','Livrée','Annulée')),
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
