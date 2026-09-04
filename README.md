# JavaShop

## Auteurs

- DUSÉPULCHRE Jaï
- PAULUS Robin
- VAN CAUTER Boris

## Présentation du projet

Ce projet contient :

- Une base de données MySQL lancée via Docker.
- Une API développée en Java, située dans le répertoire `projet-bd/`.
- Une interface Swagger permettant d'interagir avec la base de données via l'API.

L'ensemble du projet est entièrement dockerisé : **la base de données et l'API sont lancées automatiquement avec Docker Compose.**

## Lancer le projet

À la racine du projet, exécutez simplement :

```bash
docker compose up -d
```

Une fois l'application démarrée, accédez à l'adresse suivante :

http://localhost:8081/swagger-ui/index.html

Vous pourrez alors interagir avec la base de données via l'interface Swagger.
