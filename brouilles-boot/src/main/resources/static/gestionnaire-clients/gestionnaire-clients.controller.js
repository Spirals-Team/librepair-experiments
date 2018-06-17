/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('GestionnaireClientsController', ['$location','$scope',

		function ($location,$scope) {
			var controller = this;

            /**
			 * Capte un événement selectionNouveauDomaineEnvironnement.
			 */
			//~ $scope.$on('modifierModeSimplifie', function (event) {
                //~ controller.isModeSimplifie = ModeServices.getModeSimplifie();
 
			//~ });
            
			/**
			 * Permet de rendre actif l'onglet de la navbar en fonction de la page où l'on se trouve
			 * @param tab
			 * @returns true si tab est actif
			 */
			controller.isActive = function (tab) {
				if(tab === 'Clients') {
					return $location.url().endsWith('/');
				} else {
					return $location.url().endsWith(tab);
				}
			}
		}
	]);
})();
