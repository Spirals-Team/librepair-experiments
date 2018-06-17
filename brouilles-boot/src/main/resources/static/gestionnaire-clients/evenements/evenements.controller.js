/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('EvenementsController', ['$scope', '$location', 'EvenementsServices',

		function ($scope, $location, EvenementsServices) {

			var controller = this;

			initialiser();
            
			function initialiser() {
                console.log("Init EvenementsController");
                reinitialiserErreurs() ;
				controller.requettesHttpEnCours = 0;
				controller.erreur = undefined;
                $scope.evenements=[];
                getEvenements();
			}
            
            // Récupération des evenements
			function getEvenements() {
                controller.erreurGetEvenements = undefined;
				controller.requettesHttpEnCours++;
				EvenementsServices.getEvenementsAvecInfosClient().then(function(response) {
                    $scope.evenements = response.data;
                    console.log("Evenements=", $scope.evenements);
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getEvenements : ", error);
					controller.erreurGetEvenements = {libelle : "Erreur lors de la récupération des évenements", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
            function reinitialiserErreurs() {
                controller.erreurGetEvenements = undefined;
			}
            
            // Bouton creer client
            $scope.clickOnEvenement = function(evenement) {
                console.log("click clickOnEvenement:", evenement);
                var idEvenement = evenement.id;
                var idClient = evenement.idClient;
                console.log("evenement.typeEvenement=", evenement.typeEvenement);
                if (evenement.typeEvenement == 'MARIAGE') {
                    $location.path("/EditionMariage").search({idEvenement: idEvenement, idClient: idClient});
                } else if (evenement.typeEvenement == 'REPAS') {
                    $location.path("/EditionRepas").search({idEvenement: idEvenement, idClient: idClient});
                } else if (evenement.typeEvenement == 'LOCATION_SALLE') {
                    $location.path("/EditionLocation").search({idEvenement: idEvenement, idClient: idClient});
                }else if (evenement.typeEvenement == 'LIBERTE') {
                    $location.path("/EditionRepasLiberte").search({idEvenement: idEvenement, idClient: idClient});
                }else if (evenement.typeEvenement == 'CHAMBRE') {
                    $location.path("/EditionChambre").search({idEvenement: idEvenement, idClient: idClient});
                }else {
                    alert("Not managed");
                }
            };
            
		}
	]);
})();
