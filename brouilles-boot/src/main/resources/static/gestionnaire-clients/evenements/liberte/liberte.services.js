/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('RepasLiberteServices', ['$http','ConfigurationServices',
		function($http, ConfigurationServices) {

			//Interface de la factory de services
			var services = {
                postEvenement : postEvenement,
                getEvenementById : getEvenementById,
                updateEvenement : updateEvenement
			};
            
			function postEvenement(evenement) {
				var urlResource = ConfigurationServices.getUrlServeur() + "liberte";
				var promise =  $http.post(urlResource, evenement, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
			function getEvenementById(id) {
				var urlResource = ConfigurationServices.getUrlServeur() + "liberte/" + id;
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}

			function updateEvenement(evenement) {
				var urlResource = ConfigurationServices.getUrlServeur() + "liberte";
				var promise =  $http.put(urlResource, evenement, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
			return services;
		}
	]);
})();
