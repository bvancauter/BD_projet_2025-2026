-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Fri Apr  3 14:59:41 2026 
-- * LUN file: C:\Passerelle - UNamur\BDD\Projet\B2- Base de données projet.lun 
-- * Schema: Schéma logique/1-1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database Schéma physique


-- DBSpace Section
-- _______________

create dbspace USER_SPC;

create dbspace COMMANDE_SPC;

create dbspace ARTICLE_SPC;


-- Tables Section
-- _____________ 

create table Article (
     articleId -- Sequence attribute not implemented -- not null,
     nom varchar(100) not null,
     description varchar(500) not null,
     prix numeric(8,2) not null,
     Vetement numeric(10),
     Livre numeric(10),
     JeuVideo numeric(10),
     Electromenager numeric(10),
     constraint ID_Article_ID primary key (articleId));

create table Avis (
     articleId numeric(10) not null,
     commandId numeric(10) not null,
     utilisateurId numeric(10) not null,
     note numeric(1) not null,
     commentaire varchar(1000) not null,
     date date not null,
     constraint ID_Avis_ID primary key (utilisateurId, commandId, articleId));

create table Commande (
     commandId -- Sequence attribute not implemented -- not null,
     datePaiement char(10) not null,
     dateLivraison date,
     dateAnnulation date,
     statut char(1) not null,
     utilisateurId numeric(10) not null,
     constraint ID_Commande_ID primary key (commandId));

create table DemandeRemboursement (
     demandeRemboursementId -- Sequence attribute not implemented -- not null,
     commandId numeric(10) not null,
     date date not null,
     raison varchar(500) not null,
     constraint ID_DemandeRemboursement_ID primary key (demandeRemboursementId),
     constraint FKestEnLien_ID unique (commandId));

create table Electromenager (
     articleId numeric(10) not null,
     marque varchar(100) not null,
     constraint FKArt_Ele_ID primary key (articleId));

create table JeuVideo (
     articleId numeric(10) not null,
     plateforme varchar(50) not null,
     studio varchar(50) not null,
     PEGI numeric(2) not null,
     constraint FKArt_Jeu_ID primary key (articleId));

create table LigneDeCommande (
     commandId numeric(10) not null,
     articleId numeric(10) not null,
     quantite numeric(5) not null,
     constraint ID_LigneDeCommande_ID primary key (articleId, commandId));

create table Livre (
     articleId numeric(10) not null,
     auteur varchar(50) not null,
     ISBN varchar(13) not null,
     constraint SID_Livre_ID unique (ISBN),
     constraint FKArt_Liv_ID primary key (articleId));

create table Promotion (
     promotionId -- Sequence attribute not implemented -- not null,
     nom varchar(100) not null,
     description varchar(500) not null,
     pourcentage numeric(5,2) not null,
     dateDebut date,
     dateFin date,
     constraint ID_Promotion_ID primary key (promotionId));

create table Remboursement (
     remboursementId -- Sequence attribute not implemented -- not null,
     demandeRemboursementId numeric(10) not null,
     date date not null,
     constraint ID_Remboursement_ID primary key (remboursementId),
     constraint FKDonneLieuA_ID unique (demandeRemboursementId));

create table UsagePromo (
     articleId numeric(10) not null,
     promotionId numeric(10) not null,
     utilisateurId numeric(10) not null,
     date date not null,
     constraint ID_UsagePromo_ID primary key (utilisateurId, promotionId, articleId));

create table Utilisateur (
     utilisateurId -- Sequence attribute not implemented -- not null,
     email varchar(100) not null,
     prenom varchar(50) not null,
     nom varchar(50) not null,
     telephone varchar(15),
     adr_rue varchar(150) not null,
     adr_numero varchar(10) not null,
     adr_ville varchar(100) not null,
     adr_codePostal varchar(10) not null,
     motDePasse varchar(255) not null,
     methodePaiement varchar(50) not null,
     constraint ID_Utilisateur_ID primary key (utilisateurId),
     constraint SID_Utilisateur_ID unique (email));

create table Vetement (
     articleId numeric(10) not null,
     taille varchar(5) not null,
     constraint FKArt_Vet_ID primary key (articleId));


-- Constraints Section
-- ___________________ 

alter table Article add constraint EXCL_Article
     check((Livre is not null and Electromenager is null and JeuVideo is null and Vetement is null)
           or (Livre is null and Electromenager is not null and JeuVideo is null and Vetement is null)
           or (Livre is null and Electromenager is null and JeuVideo is not null and Vetement is null)
           or (Livre is null and Electromenager is null and JeuVideo is null and Vetement is not null)
           or (Livre is null and Electromenager is null and JeuVideo is null and Vetement is null)); 

alter table Avis add constraint FKAvi_Uti
     foreign key (utilisateurId)
     references Utilisateur;

alter table Avis add constraint FKAvi_Com_FK
     foreign key (commandId)
     references Commande;

alter table Avis add constraint FKAvi_Art_FK
     foreign key (articleId)
     references Article;

alter table Commande add constraint EXCL_Commande
     check((dateLivraison is not null and dateAnnulation is null)
           or (dateLivraison is null and dateAnnulation is not null)
           or (dateLivraison is null and dateAnnulation is null)); 

alter table Commande add constraint FKcommande_FK
     foreign key (utilisateurId)
     references Utilisateur;

alter table DemandeRemboursement add constraint FKestEnLien_FK
     foreign key (commandId)
     references Commande;

alter table Electromenager add constraint FKArt_Ele_FK
     foreign key (articleId)
     references Article;

alter table JeuVideo add constraint FKArt_Jeu_FK
     foreign key (articleId)
     references Article;

alter table LigneDeCommande add constraint FKcontient
     foreign key (articleId)
     references Article;

alter table LigneDeCommande add constraint FKappartient_FK
     foreign key (commandId)
     references Commande;

alter table Livre add constraint FKArt_Liv_FK
     foreign key (articleId)
     references Article;

alter table Promotion add constraint COEX_Promotion
     check((dateDebut is not null and dateFin is not null)
           or (dateDebut is null and dateFin is null)); 

alter table Remboursement add constraint FKDonneLieuA_FK
     foreign key (demandeRemboursementId)
     references DemandeRemboursement;

alter table UsagePromo add constraint FKUsa_Uti
     foreign key (utilisateurId)
     references Utilisateur;

alter table UsagePromo add constraint FKUsa_Pro_FK
     foreign key (promotionId)
     references Promotion;

alter table UsagePromo add constraint FKUsa_Art_FK
     foreign key (articleId)
     references Article;

alter table Vetement add constraint FKArt_Vet_FK
     foreign key (articleId)
     references Article;


-- Index Section
-- _____________ 

create unique index ID_Article_IND
     on Article (articleId);

create unique index ID_Avis_IND
     on Avis (utilisateurId, commandId, articleId);

create index FKAvi_Com_IND
     on Avis (commandId);

create index FKAvi_Art_IND
     on Avis (articleId);

create unique index ID_Commande_IND
     on Commande (commandId);

create index FKcommande_IND
     on Commande (utilisateurId);

create unique index ID_DemandeRemboursement_IND
     on DemandeRemboursement (demandeRemboursementId);

create unique index FKestEnLien_IND
     on DemandeRemboursement (commandId);

create unique index FKArt_Ele_IND
     on Electromenager (articleId);

create unique index FKArt_Jeu_IND
     on JeuVideo (articleId);

create unique index ID_LigneDeCommande_IND
     on LigneDeCommande (articleId, commandId);

create index FKappartient_IND
     on LigneDeCommande (commandId);

create unique index SID_Livre_IND
     on Livre (ISBN);

create unique index FKArt_Liv_IND
     on Livre (articleId);

create unique index ID_Promotion_IND
     on Promotion (promotionId);

create unique index ID_Remboursement_IND
     on Remboursement (remboursementId);

create unique index FKDonneLieuA_IND
     on Remboursement (demandeRemboursementId);

create unique index ID_UsagePromo_IND
     on UsagePromo (utilisateurId, promotionId, articleId);

create index FKUsa_Pro_IND
     on UsagePromo (promotionId);

create index FKUsa_Art_IND
     on UsagePromo (articleId);

create unique index ID_Utilisateur_IND
     on Utilisateur (utilisateurId);

create unique index SID_Utilisateur_IND
     on Utilisateur (email);

create unique index FKArt_Vet_IND
     on Vetement (articleId);

