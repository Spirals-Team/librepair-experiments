# TravisCI_Experimentation
This repository is a 4/5hours homework for testing the CI/CD software named Travis CI.
It's supposed to create a CI for a random java project.


## Documentation du Pipeline

### Objectifs

On souhaite établir un pipeline de livraison : une série d'étape Build/Test jusqu'à demander une validation 
manuelle de la part d'un utilisateur avant le déploiement sur la branche master.

![Pipeline](https://github.com/iesseddik/TravisCI_Experimentation/blob/master/pipeline.png?raw=true)


Lien du dépôt de code du projet sur github : https://github.com/iesseddik/TravisCI_Experimentation (vous y êtes)

Lien d'accès a l'outil de CI/CD : https://travis-ci.org/iesseddik/TravisCI_Experimentation

### Fonctionnement de Travis

Pour régler les fonctionnalités de l'outil Travis, il existe un fichier nommé ".travis.yml" que l'on doit placer dans le dépôt du code pour parser et identifier sa structure. Ce dernier contient des instructions obligatoires à mentionner et d'autres qui sont optionnelles. Les dépendances Maven sont automatiquement récupérées par defaut.

Dans notre fichier .yml on y trouve par exemple:

-language: java 
(pour indiquer le langage du code source du projet)

-sudo: false 
(pour configurer les droits utilisateur)

-install: true 
(on indique ici la valeur a true pour sauter l'étape)

script:mvn compile (ici on entre les commandes shell a exécuter, par exemple pour la compilation).

Ainsi, a chaque pull/merge Request sur ce dépôt, Travis se chargera de la bonne exécution de chaque étape du pipeline decrit dans le fichier yml.

### Conclusion

Ayant dépassé les 5 heures de travail je n'ai pas encore pu trouver comment exécuter le deploiement avant de pouvoir le faire manuellement.
