/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('DevisServices', ['$http', 'ConfigurationServices', 
		function($http, ConfigurationServices) {

			//Interface de la factory de services
			var services = {
                getDevis : getDevis,
                getDevisByClientId : getDevisByClientId,
                getDevisByEvenementId : getDevisByEvenementId,
                postDevis : postDevis,
                getProprietesDevisMariage : getProprietesDevisMariage,
                getDevisById : getDevisById,
                updateDevis : updateDevis
			};

			function getDevis() {
                var urlResource = ConfigurationServices.getUrlServeur() + "devis";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getDevisByClientId(idClient) {
                var urlResource = ConfigurationServices.getUrlServeur() + "devis/client/" + idClient;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getDevisByEvenementId(idEvenement) {
                var urlResource = ConfigurationServices.getUrlServeur() + "devis/evenement/" + idEvenement;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getDevisById(idDevis) {
                var urlResource = ConfigurationServices.getUrlServeur() + "devis/" + idDevis;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function postDevis(devis) {
				var urlResource = ConfigurationServices.getUrlServeur() + "devis";
				var promise =  $http.post(urlResource, devis, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
			function getProprietesDevisMariage() {
                var urlResource = ConfigurationServices.getUrlServeur() + "proprietesdevismariage";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function updateDevis(devis) {
				var urlResource = ConfigurationServices.getUrlServeur() + "devis";
				var promise =  $http.put(urlResource, devis, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}            
            
			return services;
		}
	]);
})();
