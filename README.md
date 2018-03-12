# ipi-java-350-ex
 Exercices de Java, module 350 pour l'IPI. Commencer par créer une branche à partir de master sur laquelle vous allez travailler. Lorsque tous les exercices sont terminés et que vous voulez soumettre votre travail, vous n'aurez plus qu'à créer une Pull Request de votre branche sur master. J'utiliserai cette Pull Request pour faire la code review de votre travail.

- Intégration continue

   - Rajouter la configuration nécessaire pour Travis dans le projet.
   - Vous connecter à Travis https://travis-ci.org avec votre compte Github.
   - Configurer le projet et vérifier que le premier build se passe correctement. Après chaque exercice, vérifier que le build passe toujours...
 
- Evaluation de la qualité
   - Connectez-vous à SonarQube https://about.sonarcloud.io/ avec votre compte Github
   - Ajouter votre projet dans Sonar
   - Modifier votre configuration Travis pour lancer une analyse après chaque build
   - Vérifier que tout est ok
   - Analyser le premier rapport de Sonar

- Tests unitaires
   - Créer la classe `CommercialTest` dans le bon package pour tester quelques méthodes de la classe `Commercial`.
   - Tester le plus complètement possible la méthode permettant de récupérer la prime annuelle
   - Créer la classe `CommercialParameterizedTest` dans le même package et tester complètement et de manière paramétrée la méthode `equivalenceNote`.
   - Ajouter le fichier `application.properties` de test pour utiliser une base de données mémoire H2
   - Créer la classe `EmployeRepositoryTest` et tester la méthode `findByNomOrPrenomAllIgnoreCase` le plus complètement possible.
   - Ajouter une méthode `before` supprimant le contenu des tables manipulées dans les tests.
   - Créer la classe `EmployeServiceTest` et tester la méthode `findByMatricule` en mockant l'utilisation de la base de données.
   - Créer la classe `TechnicienServiceTest` et tester la méthode `addManager` en vérifiant les paramètres passés aux méthodes save des repository pour s'assurer que le manager a été ajouté au technicien et inversement.
- Tests d'intégration
   - Créer la classe `ManagerServiceTest` et tester de manière intégrée la méthode `addTechniciens`

Evaluation

- Tester (sous forme de tests paramétrés) la méthode `setSalaire`, `augmenterSalaire` et/ou `getPrimeAnnuelle` de `Manager.java`
- Tester de manière intégrée la méthode `findEmployesPlusRiches` (3 cas de test minimum)
- Tester de manière mockée la méthode `addTechnicien` de `ManagerService`
