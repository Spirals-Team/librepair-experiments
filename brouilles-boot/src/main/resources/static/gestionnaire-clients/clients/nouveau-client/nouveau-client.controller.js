/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('NouveauClientController', ['$scope', '$location',
																																	 'NouveauClientServices', 'ClientsServices', 'PropertiesServices',

		function ($scope, $location, NouveauClientServices, ClientsServices, PropertiesServices) {

			var controller = this;
            $scope.submitted = false;
            
            $scope.client={"denomination":"A RENSEIGNER"};

            $scope.categories = PropertiesServices.getCategories();

			initialiser();

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                controller.erreurPostClient = undefined;
			}
            
            function redirigerPageRechercheClients() {
                $location.path("/Clients").search({empty : 'true'}); 
            }
            
            function redirigerPageEditionClientAvecId(clientId) {
                console.log("Redirection vers page edition pour client avec id:",clientId);
                $location.path("/EditionClient").search({id: clientId}); 
            }
            
             function postClient(client) {
                controller.erreurPostClient = undefined;
				controller.requettesHttpEnCours++;
                 console.log("Avant post");
				ClientsServices.postClient($scope.client).then(function(response) {
                    console.log("response post :", response);
					controller.requettesHttpEnCours--;
                    redirigerPageEditionClientAvecId(response.data.id);
				}, function (error) {
                    console.error("Erreur post ", error);
                    controller.erreurPostClient = {libelle : "Erreur lors de la creation du client ", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
            $scope.annulerClient = function(path) {
                console.log("annulerClient");
                redirigerPageRechercheClients();
            };
            
            $scope.validerClient = function(path) {
                console.log("validerClient");
                $scope.submitted = true;
                if (!$scope.formulaireClient.$valid) {
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : creation du client");
                    postClient($scope.client);
                }
            };
            
		}
	]);
})();
