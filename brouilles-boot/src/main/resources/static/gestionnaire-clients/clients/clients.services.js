/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('ClientsServices', ['$http', 'ConfigurationServices',
		function($http, ConfigurationServices) {

			//Interface de la factory de services
			var services = {
                getClients : getClients,
                getClient : getClient,
                updateClient : updateClient,
                postClient : postClient
			};
            
			function getClients() {
                var urlResource = ConfigurationServices.getUrlServeur() + "clients";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
            function getClient(idClient) {
                var urlResource = ConfigurationServices.getUrlServeur() + "clients/" + idClient;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function postClient(client) {
				var urlResource = ConfigurationServices.getUrlServeur() + "clients";
				var promise =  $http.post(urlResource, client, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
            function updateClient(client) {
				var urlResource = ConfigurationServices.getUrlServeur() + "clients/" + client.id;
				var promise =  $http.put(urlResource, client, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}

			return services;
		}
	]);
})();
