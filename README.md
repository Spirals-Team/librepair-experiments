[![Build Status](https://travis-ci.org/paul604/quizz-movie.svg?branch=master)](https://travis-ci.org/paul604/quizz-movie)
[![sonarcloud](https://sonarcloud.io/api/project_badges/measure?project=fr.iut.nantes%3Aquizz-movie&metric=alert_status)](https://sonarcloud.io/dashboard?id=fr.iut.nantes%3Aquizz-movie)
[![codecov](https://codecov.io/gh/paul604/quizz-movie/branch/master/graph/badge.svg)](https://codecov.io/gh/paul604/quizz-movie)
[![GitHub tag](https://img.shields.io/github/tag/paul604/quizz-movie.svg)](https://github.com/paul604/quizz-movie/releases)
[![GitHub issues](https://img.shields.io/github/issues/paul604/quizz-movie.svg)](https://github.com/paul604/quizz-movie/issues)
[![license](https://img.shields.io/github/license/paul604/quizz-movie.svg)](https://github.com/paul604/quizz-movie/blob/master/LICENSE)

# Quizz movie

## Description
Quizz movie est une application distributrice de service en JSON, permettant de
jouer à un quiz sur les films de la base de données d’OMDB et de noter les statistiques des joueurs.

Notre application se découpe en 3 parties majeurs : le [front](#front-folder), [Spring](#spring-folder), et la [base de données](#base-de-données-web).

### Front ([Folder](./front-end/))
Le front est composé de vues permettant à un utilisateurs de comprendre facilement le fonctionnement du programme. Il peut s’y authentifier, créer un compte, demander une question et y répondre. Il est paramétré pour utiliser les services du back afin de les rendre accessible à un utilisateur lambda.   
https://paul604.github.io/quizz-movie/

### Spring ([Folder](./back-end/))
Spring est une partie composé à la fois de spring mvc qui met en place les service du serveur et des contrôleurs qui sont les opérateurs du programme. Les contrôleurs permettre d’accéder à la partie base de données et d’effectuer des appels sur des API externes.   
API doc: https://paul604.github.io/quizz-movie/doc

### Base de données ([web](https://www.mongodb.com))
Enfin, la base de données est une base de données mongoDB. Elle stock les statistiques sur les joueurs et est appelée par les contrôleurs.

## Déploiement
Pre-requis :
   * serveur tomcat
   * serveur mongo DB
   * le war du projet.
   
Mettre le war dans le dossier webapps du serveur tomcat, puis le lancer.
   
Une fois un 1er lancement effectué, vous pourrez (sans obligation) paramétrer la base de données dans le fichier application.properties. Cependant, toute modification implique un redémarrage du serveur tomcat.

# Developers
* @crowlas
* @Kl000
* @paul604


# Legal

| Name | Type | License |
|:---:|:---:|:---:|
| Quizz movie | Repot GitHub | [MIT](https://github.com/paul604/quiz-omdb/blob/master/LICENSE) |
| [OMDB](https://www.omdbapi.com/)| API Rest | [![cc-BY-NC](https://mirrors.creativecommons.org/presskit/buttons/88x31/svg/by-nc.svg)](https://creativecommons.org/licenses/by-nc/4.0/)|
| [junit](https://junit.org/junit4/) | API Back-end | [Eclipse Public License - v 1.0](https://junit.org/junit4/license.html) |
| [SpringBoot](https://projects.spring.io/spring-boot/) | API Back-end | [Apache License 2.0](https://github.com/spring-projects/spring-boot/blob/master/LICENSE.txt) |
| [jjwt](https://github.com/jwtk/jjwt) | API Back-end | [Apache License 2.0](https://github.com/jwtk/jjwt/blob/master/LICENSE) |
| [mongodb](https://www.mongodb.com) | API Back-end | [GNU AGPL v3.0.](http://www.gnu.org/licenses/agpl-3.0.html) |
| [SLF4J](https://www.slf4j.org/) | API Back-end | [MIT](https://www.slf4j.org/license.html) |
| [gson](https://github.com/google/gson) | API Back-end | [Apache License 2.0](https://github.com/google/gson/blob/master/LICENSE) |
| [Vue.js](https://vuejs.org/)| API Front-end | [MIT](https://github.com/vuejs/vue/blob/dev/LICENSE) |
