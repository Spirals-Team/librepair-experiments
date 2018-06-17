/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('FacturesController', ['$scope','FacturesServices',

		function ($scope, FacturesServices) {

			var controller = this;
            
			initialiser();

			function initialiser() {
                console.log("Init FacturesController");
                reinitialiserErreurs() ;
				controller.requettesHttpEnCours = 0;
				controller.erreur = undefined;
                getFactures();
			}
            
            // Récupération des factures
			function getFactures() {
                controller.erreurGetFactures = undefined;
				controller.requettesHttpEnCours++;
				FacturesServices.getFactures().then(function(response) {
                    controller.factures = response.data;
                    console.log("factures=", controller.factures);
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getFactures : ", error);
					controller.erreurGetFactures = {libelle : "Erreur lors de la récupération des factures", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                    controller.requettesHttpEnCours--;
				});
			}
            
            function reinitialiserErreurs() {
                controller.erreurGetFactures = undefined;
			}
            
		}
	]);
})();
