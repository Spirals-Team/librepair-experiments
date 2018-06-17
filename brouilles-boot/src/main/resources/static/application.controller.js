/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('ApplicationController', ['$scope', '$timeout', '$location', '$http', 'ConfigurationServices',

		function ($scope, $timeout, $location, $http, ConfigurationServices) {
            
            var controller = this;
            
            initialiser();
            
			function initialiser() {
			}
            
            $scope.accueil = function() {
                console.log("click");
                $location.path("/Accueil"); 
            };
            
		}
	]);
})();
