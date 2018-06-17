/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('FacturesServices', ['$http',
		function($http) {

			//Interface de la factory de services
			var services = {
                getFactures, getFactures
			};

            function getFactures() {
                var urlResource = "http://localhost:8787/brouilles/factures";
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
			return services;
		}
	]);
})();
