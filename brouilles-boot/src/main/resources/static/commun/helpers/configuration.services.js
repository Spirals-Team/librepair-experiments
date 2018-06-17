/**
 * Created by ijla5100 on 23/12/2016.
 */
(function() {

	"use strict";

	angular
			.module('ApplicationGestionnaireClients')
			.factory(
					'ConfigurationServices',
					[
							'$http',

							function($http) {

								// Interface de la factory de services
								var services = {
									getUrlServeur : getUrlServeur
								};
                                
                                function getUrlServeur() {
                                    return "http://localhost:8787/brouilles/";
                                }

								return services;
							} ]);
})();