/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('EvenementsServices', ['$http', 'ConfigurationServices',
		function($http, ConfigurationServices) {

			//Interface de la factory de services
			var services = {
                getEvenements : getEvenements,
                getEvenementsAvecInfosClient : getEvenementsAvecInfosClient,
                getEvenement : getEvenement,
                postEvenement : postEvenement,
                updateEvenement : updateEvenement,
                getEvenementsDuClient : getEvenementsDuClient,
                getEvenementsInRange : getEvenementsInRange,
                getEvenementsSemaine : getEvenementsSemaine,
                getEvenementsDuJour : getEvenementsDuJour,
                getEvenementsDuJourAvecTypes : getEvenementsDuJourAvecTypes
			};
            
			function getEvenements() {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getEvenementsAvecInfosClient() {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements/avecInfosClient";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getEvenementsInRange(debut, fin) {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements/range?debut="+debut+"&fin="+fin;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getEvenementsDuJour() {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements/jour";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getEvenementsDuJourAvecTypes(date, types) {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements/date/" + date + "?types=" + types;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getEvenementsSemaine() {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements/semaine"
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getEvenementsDuClient(idClient) {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements/client/" + idClient;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
            function getEvenement(idEvenement) {
                var urlResource = ConfigurationServices.getUrlServeur() + "evenements/" + idEvenement;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function postEvenement(evenement) {
				var urlResource = ConfigurationServices.getUrlServeur() + "evenements";
				var promise =  $http.post(urlResource, evenement, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
            function updateEvenement(evenement) {
				var urlResource = ConfigurationServices.getUrlServeur() + "evenements/" + evenement.id;
				var promise =  $http.put(urlResource, evenement, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}

			return services;
		}
	]);
})();
