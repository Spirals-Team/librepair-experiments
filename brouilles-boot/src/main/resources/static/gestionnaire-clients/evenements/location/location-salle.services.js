/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('LocationSalleServices', ['$http','ConfigurationServices',
		function($http, ConfigurationServices) {

			//Interface de la factory de services
			var services = {
                postEvenement : postEvenement,
                updateEvenement : updateEvenement,
                getEvenementById : getEvenementById
			};
            
			function postEvenement(evenement) {
				var urlResource = ConfigurationServices.getUrlServeur() + "location";
				var promise =  $http.post(urlResource, evenement, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
			function updateEvenement(evenement) {
				var urlResource = ConfigurationServices.getUrlServeur() + "location";
				var promise =  $http.put(urlResource, evenement, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}

			function getEvenementById(id) {
				var urlResource = ConfigurationServices.getUrlServeur() + "location/" + id;
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}

			return services;
		}
	]);
})();
