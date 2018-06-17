/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('PostITServices', ['$http','ConfigurationServices',
		function($http, ConfigurationServices) {

			//Interface de la factory de services
			var services = {
                postEvenement : postEvenement,
                getPostIts : getPostIts,
                getPostItsDuJour : getPostItsDuJour,
                update : update,
                remove : remove,
                getPostItById : getPostItById
			};

			function postEvenement(postit) {
				var urlResource = ConfigurationServices.getUrlServeur() + "postit";
				var promise =  $http.post(urlResource, postit, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
			function update(postit) {
				var urlResource = ConfigurationServices.getUrlServeur() + "postit";
				var promise =  $http.put(urlResource, postit, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
			function remove(id) {
				var urlResource = ConfigurationServices.getUrlServeur() + "postit/" + id;
				var promise =  $http.delete(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
				return promise;
			}
            
			function getPostIts() {
                var urlResource = ConfigurationServices.getUrlServeur() + "postit";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getPostItById(id) {
                var urlResource = ConfigurationServices.getUrlServeur() + "postit/" + id;
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			function getPostItsDuJour() {
                var urlResource = ConfigurationServices.getUrlServeur() + "postit/jour";
                console.log("urlResource:",urlResource);
				var promise =  $http.get(urlResource, {headers: {}}).success(function(response) {
					return response;
				});
                return promise;
            }
            
			return services;
		}
	]);
})();
