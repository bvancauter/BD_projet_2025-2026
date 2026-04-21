# BD - projet 2025-2026

## Auteurs

- DUSÉPULCHRE Jaï
- PAULUS Robin
- VAN CAUTER Boris

## Présentation du projet

Ce projet contient :

- Une base de données MySQL lancée via Docker.
- Une API développée en Java, située dans le répertoire `projet-bd/`.
- Une interface Swagger permettant d'interagir avec la base de données via l'API.

## Lancer la base de données avec Docker

Pour démarrer la base de données, exécutez la commande suivante à la racine du projet :

```bash
docker compose up -d
```

Cette commande permet de :

- Lancer un conteneur MySQL.
- Créer automatiquement la base de données.
- Importer les scripts SQL du projet.
- Générer les différentes tables.
- Ajouter les triggers, vues et permissions nécessaires.

## Lancer l'API Java

Le dossier `projet-bd/` contient la conception de notre API développée en Java.

Étapes :

1. Ouvrir le dossier `projet-bd/` avec IntelliJ IDEA.
2. Lancer le projet depuis IntelliJ.
3. Utiliser Swagger

Une fois l'API démarrée, rendez-vous à l'adresse suivante :

http://localhost:8081/swagger-ui/index.html

Vous pourrez alors interagir avec la base de données via l'interface Swagger.
