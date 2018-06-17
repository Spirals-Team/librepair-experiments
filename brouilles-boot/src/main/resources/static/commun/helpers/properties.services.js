/**
 * Created by ijla5100 on 23/12/2016.
 */
(function() {

	"use strict";

	angular
			.module('ApplicationGestionnaireClients')
			.factory(
					'PropertiesServices',
					[
							'$http',

							function($http) {

								// Interface de la factory de services
								var services = {
									getCategories : getCategories,
                                    getTypeEvenements : getTypeEvenements,
                                    getEtatEvenements : getEtatEvenements,
                                    getEvenementFromTypeEvenement : getEvenementFromTypeEvenement,
                                    getEtatEvtColorFromValeur : getEtatEvtColorFromValeur,
                                    getTypesTables : getTypesTables
								};
                                
                                function getCategories() {
                                    return [
                                        {label:'Particulier', valeur:'PARTICULIER'},
                                        {label:'Association', valeur:'ASSOCIATION'},
                                        {label:'Entreprise', valeur:'ENTREPRISE'},
                                        {label:'Autres', valeur:'AUTRES'}
                                        ];
                                }
                                
                                function getTypeEvenements() {
                                    return [
                                        {label:'Mariage', valeur:'MARIAGE'},
                                        {label:'Location de salle', valeur:'LOCATION_SALLE'},
                                        {label:'Repas', valeur:'REPAS'},
                                        {label:'Liberté', valeur:'LIBERTE'},
                                        {label:'Chambre', valeur:'CHAMBRE'}
                                        ];
                                }
                                
                                function getEtatEvenements() {
                                    return [
                                        {label:'Pré-réservé', valeur:'PRE_RESERVE', color : '#0033cc'},
                                        {label:'Confirmé', valeur:'CONFIRME', color : '#1fad1f'},
                                        {label:'Annulé', valeur:'ANNULE', color : '#cc3300'}
                                        ];
                                }
                                
                                function getTypesTables() {
                                    return [
                                        {label:'Rondes', valeur:'RONDES'},
                                        {label:'Carrées', valeur:'CARREES'}
                                        ];
                                }
                                
                                function getEvenementFromTypeEvenement(type) {
                                    var nom = "Inconnu"
                                    if (type == 'LOCATION_SALLE') {
                                        nom = "Location"
                                    } else if (type == 'MARIAGE') {
                                        nom = "Mariage"
                                    }else if (type == 'REPAS') {
                                        nom = "Repas"
                                    }else if (type == 'CHAMBRE') {
                                        nom = "Chambre"
                                    }else if (type == 'LIBERTE') {
                                        nom = "Liberté"
                                    }
                                    return nom;
                                }
                                
                                function getEtatEvtColorFromValeur(valeur) {
                                    var color ='transparent';
                                    var etatsEvenement = getEtatEvenements();
                                    angular.forEach(etatsEvenement, function(etatEvenement, index) {
                                        if (valeur == etatEvenement.valeur) {
                                            color = etatEvenement.color;
                                        }
                                    });
                                    return color;
                                }

								return services;
							} ]);
})();