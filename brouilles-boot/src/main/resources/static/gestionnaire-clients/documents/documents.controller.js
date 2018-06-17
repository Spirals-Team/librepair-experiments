/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('DocumentsController', ['$scope',
																																	 'DocumentsServices',

		function ($scope, DocumentsServices) {

			var controller = this;            
            controller.showMariage = true;
            controller.showAssociation = false;
            controller.showRepas = false;
            controller.showSeminaire = false;
            controller.showLiberte = false;
            controller.showBuffet = false;
			initialiser();

			function initialiser() {
				controller.requettesHttpEnCours = 0;
			}
		}
	]);
})();
