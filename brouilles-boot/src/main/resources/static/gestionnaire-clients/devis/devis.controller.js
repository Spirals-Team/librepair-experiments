/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('DevisController', ['$scope','$window', 'DevisServices',

		function ($scope, $window, DevisServices) {

			var controller = this;            
			initialiser();

			function initialiser() {
                reinitialiserErreurs() ;
				controller.requettesHttpEnCours = 0;
				controller.erreur = undefined;
                getDevis();
			}
            
            function reinitialiserErreurs() {
                controller.erreurGetDevis = undefined;
			}
            
            // Récupération des devis
			function getDevis() {
                controller.erreurGetDevis = undefined;
				controller.requettesHttpEnCours++;
				DevisServices.getDevis().then(function(response) {
                    controller.devis = response.data;
                    console.log("devis=", controller.devis);
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getDevis : ", error);
					controller.erreurGetDevis = {libelle : "Erreur lors de la récupération des devis", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
            $scope.afficherDevis = function (devis) {
                console.log("afficherDevis");
                $window.open(devis.url);
            };
            // TEST
            $scope.testPDF = function () {
                console.log("Test pdf");
                $window.open('/home/julien/Divers/test_angular.pdf');
            };
		}
	]);
})();
