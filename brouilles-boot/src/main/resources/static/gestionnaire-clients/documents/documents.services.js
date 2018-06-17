/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').factory('DocumentsServices', ['$http', '$window', 'ConfigurationServices', 
		function($http, $window, ConfigurationServices) {

			//Interface de la factory de services
			var services = {
                downloadDevisInPdf : downloadDevisInPdf
			};
            
			function downloadDevisInPdf(devis) {
				var urlDevisPdf = ConfigurationServices.getUrlServeur() + "documents/devisMariagePdf/" + devis.id;
                console.log("urlDevisPdf=", urlDevisPdf);
                $window.open(urlDevisPdf);
			}            

			return services;
		}
	]);
})();
