(function() {

	angular.module('ApplicationGestionnaireClients').directive("animationLoading",
		     function() {
			    return {
			    	restrict: 'E',
			    	scope: {
			    	      texte: "@texte"
			    	},
			    	templateUrl: 'commun/templates/animation-loading/animation-loading.html'
			    };
			 }
	);

	angular.module('ApplicationGestionnaireClients').directive("erreurPersonnalisee",
		function() {
			return {
				restrict: 'E',
				scope: {
					erreur : '=ngModel',
				},
				templateUrl: 'commun/templates/erreur/erreur.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("infosClient",
		function() {
			return {
				restrict: 'E',
                scope: false,
				templateUrl: 'commun/templates/client/infos-client.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("tableEvenements",
		function() {
			return {
				restrict: 'E',
                scope: {
                            evenements: '=evenements',
                            click: "&"
                        },
				templateUrl: 'commun/templates/evenement/table-evenements.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("tableEvenementsAvecInfosClient",
		function() {
			return {
				restrict: 'E',
                scope: {
                            evenements: '=evenements',
                            click: "&"
                        },
				templateUrl: 'commun/templates/evenement/table-evenements-avec-infos-client.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("formulaireMariage",
		function() {
			return {
				restrict: 'E',
                scope: false,
				templateUrl: 'commun/templates/evenement/formulaire-mariage.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("formulaireRepas",
		function() {
			return {
				restrict: 'E',
                scope: false,
				templateUrl: 'commun/templates/evenement/formulaire-repas.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("formulaireLocation",
		function() {
			return {
				restrict: 'E',
                scope: false,
				templateUrl: 'commun/templates/evenement/formulaire-location.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("formulaireLiberte",
		function() {
			return {
				restrict: 'E',
                scope: false,
				templateUrl: 'commun/templates/evenement/formulaire-liberte.html'
			};
		}
	);

	angular.module('ApplicationGestionnaireClients').directive("formulaireChambre",
		function() {
			return {
				restrict: 'E',
                scope: false,
				templateUrl: 'commun/templates/evenement/formulaire-chambre.html'
			};
		}
	);

	angular.module('ApplicationGestionnaireClients').directive("tableDevis",
		function() {
			return {
				restrict: 'E',
                scope: {
                            devis: '=devis',
                            click: "&"
                        },
				templateUrl: 'commun/templates/devis/table-devis.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("formulaireDevisMariage",
		function() {
			return {
				restrict: 'E',
                scope: false,
				templateUrl: 'commun/templates/devis/formulaire-devis-mariage.html'
			};
		}
	);
        
	angular.module('ApplicationGestionnaireClients').directive("bootstrapSelectpicker",
		function() {
			return {
				restrict : 'A',
                link: function(scope, element, attr){
                    $(element).selectpicker();
                }   
			};
		}
	);

	angular.module('ApplicationGestionnaireClients').directive("filtreForm",
			function() {
	    		return {
	    			restrict: 'E',
	    			scope: {
	    				parametreFiltre: '=ngModel',
	    				texte : '@texte'
			    	},
	    			templateUrl: 'commun/templates/filtre/filtre-form.html'
	    		};
	 		}
	);
})();
