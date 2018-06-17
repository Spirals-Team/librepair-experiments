/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('ClientsController', ['$scope', '$location', '$uibModal', 'ClientsServices','PropertiesServices',

		function ($scope, $location, $uibModal, ClientsServices, PropertiesServices) {

			var controller = this;
            controller.reverseSortClients = false;
            controller.filtreClients= "";

			initialiser();
            
			function initialiser() {
                
                console.log("Init ClientsController");
                reinitialiserErreurs() ;
				controller.requettesHttpEnCours = 0;
				controller.erreur = undefined;
                getClients();
                
                $scope.categories = PropertiesServices.getCategories();
                // On ajoute la categories Toutes au debut de la liste
                $scope.categories.unshift({label:'Toutes', valeur: undefined});
			}
            
            // Récupération des clients
			function getClients() {
                controller.erreurgetClients = undefined;
				controller.requettesHttpEnCours++;
				ClientsServices.getClients().then(function(response) {
                    controller.clients = response.data;
                    console.log("clients=", controller.clients);
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getClients : ", error);
					controller.erreurGetClients = {libelle : "Erreur lors de la récupération des clients", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
            function reinitialiserErreurs() {
                controller.erreurgetClients = undefined;
			}
            
            // Bouton creer client
            $scope.creerNouveauClient = function() {
                console.log("click creerNouveauClient");
                $location.path("/NouveauClient").search({empty : 'true'}); 
            };
            
            // Bouton creer client
            $scope.clickOnClient = function(client) {
                console.log("click clickOnClient:", client);
                $location.path("/EditionClient").search({id: client.id}); 
            };
		}
	]);
})();
