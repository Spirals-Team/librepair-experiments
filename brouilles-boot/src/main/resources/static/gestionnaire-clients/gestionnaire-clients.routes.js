/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').config(['$routeProvider',
		function($routeProvider) {

		$routeProvider
            
            // Accueil
            .when('/', {
				controller : 'AccueilController',
				controllerAs : 'accueilCtl',
				templateUrl: 'gestionnaire-clients/accueil/accueil.html'
			}).when('/Accueil', {
				controller : 'AccueilController',
				controllerAs : 'accueilCtl',
				templateUrl: 'gestionnaire-clients/accueil/accueil.html'
			})
            
            // Clients
            .when('/Clients', {
				controller : 'ClientsController',
				controllerAs : 'clientsCtl',
				templateUrl: 'gestionnaire-clients/clients/clients.html'
			}).when('/NouveauClient', {
				controller : 'NouveauClientController',
				controllerAs : 'nouveauClientCtl',
				templateUrl: 'gestionnaire-clients/clients/nouveau-client/nouveau-client.html'
			}).when('/EditionClient', {
				controller : 'EditionClientController',
				controllerAs : 'editionClientCtl',
				templateUrl: 'gestionnaire-clients/clients/edition-client/edition-client.html'
			})
            
            // Evenements
            .when('/Evenements', {
				controller : 'EvenementsController',
				controllerAs : 'evenementsCtl',
				templateUrl: 'gestionnaire-clients/evenements/evenements.html'
			}).when('/NouvelEvenement', {
				controller : 'NouvelEvenementController',
				controllerAs : 'nouvelEvenementCtl',
				templateUrl: 'gestionnaire-clients/evenements/nouvel-evenement/nouvel-evenement.html'
			}).when('/EditionEvenement', {
				controller : 'EditionEvenementController',
				controllerAs : 'editionEvenementCtl',
				templateUrl: 'gestionnaire-clients/evenements/edition-evenement/edition-evenement.html'
			}).when('/NouvelleChambre', {
				controller : 'NouvelleChambreController',
				controllerAs : 'nouvelleChambreCtl',
				templateUrl: 'gestionnaire-clients/evenements/chambres/nouvelle-chambre/nouvelle-chambre.html'
			}).when('/EditionChambre', {
				controller : 'EditionChambreController',
				controllerAs : 'editionChambreCtl',
				templateUrl: 'gestionnaire-clients/evenements/chambres/edition-chambre/edition-chambre.html'
			}).when('/NouveauRepas', {
				controller : 'NouveauRepasController',
				controllerAs : 'nouveauRepasCtl',
				templateUrl: 'gestionnaire-clients/evenements/repas/nouveau-repas.html'
			}).when('/NouveauRepasLiberte', {
				controller : 'NouveauRepasLiberteController',
				controllerAs : 'nouveauRepasLiberteCtl',
				templateUrl: 'gestionnaire-clients/evenements/liberte/nouveau-liberte/nouveau-liberte.html'
			}).when('/EditionRepasLiberte', {
				controller : 'EditionRepasLiberteController',
				controllerAs : 'editionRepasLiberteCtl',
				templateUrl: 'gestionnaire-clients/evenements/liberte/edition-liberte/edition-liberte.html'
			}).when('/NouvelleLocation', {
				controller : 'NouvelleLocationController',
				controllerAs : 'nouvelleLocationCtl',
				templateUrl: 'gestionnaire-clients/evenements/location/nouvelle-location/nouvelle-location-salle.html'
			}).when('/EditionLocation', {
				controller : 'EditionLocationController',
				controllerAs : 'editionLocationController',
				templateUrl: 'gestionnaire-clients/evenements/location/edition-location/edition-location-salle.html'
			}).when('/NouveauMariage', {
				controller : 'NouveauMariageController',
				controllerAs : 'nouveauMariageCtl',
				templateUrl: 'gestionnaire-clients/evenements/mariage/nouveau-mariage/nouveau-mariage.html'
			}).when('/EditionMariage', {
				controller : 'EditionMariageController',
				controllerAs : 'editionMariageCtl',
				templateUrl: 'gestionnaire-clients/evenements/mariage/edition-mariage/edition-mariage.html'
			}).when('/EditionRepas', {
				controller : 'EditionRepasController',
				controllerAs : 'editionMariageCtl',
				templateUrl: 'gestionnaire-clients/evenements/repas/edition-repas/edition-repas.html'
			})
            
            // Devis
            .when('/NouveauDevisMariage', {
				controller : 'NouveauDevisMariageController',
				controllerAs : 'nouveauDevisMariageController',
				templateUrl: 'gestionnaire-clients/devis/mariage/nouveau-devis/nouveau-devis-mariage.html'
            }).when('/EditionDevisMariage', {
				controller : 'EditionDevisMariageController',
				controllerAs : 'editionDevisMariageController',
				templateUrl: 'gestionnaire-clients/devis/mariage/edition-devis/edition-devis-mariage.html'
            })
            
            // Calendrier
            .when('/Calendrier', {
				controller : 'CalendrierController',
				controllerAs : 'calendrierCtl',
				templateUrl: 'gestionnaire-clients/calendrier/calendrier.html'
			})
            
            // Factures
            .when('/Factures', {
				controller : 'FacturesController',
				controllerAs : 'facturesCtl',
				templateUrl: 'gestionnaire-clients/factures/factures.html'
			})
            
            // Documents
            .when('/Documents', {
				controller : 'DocumentsController',
				controllerAs : 'documentsCtl',
				templateUrl: 'gestionnaire-clients/documents/documents.html'
			})
            
            // Erreur
            .otherwise({
                templateUrl: 'gestionnaire-clients/erreur/erreur.html'
            })
		}
	]);
})();
