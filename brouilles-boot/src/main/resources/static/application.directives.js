/**
 * Created by ijla5100 on 23/12/2016.
 */
(function() {
	'use strict';

	angular.module('ApplicationGestionnaireClients').directive("gestionnaireClients",
		function() {
			return {
				controller : "GestionnaireClientsController",
				controllerAs : "gestionnaireClientsCtl",
				restrict: 'E',
				templateUrl: 'gestionnaire-clients/gestionnaire-clients.html'
			};
		}
	);
})();


