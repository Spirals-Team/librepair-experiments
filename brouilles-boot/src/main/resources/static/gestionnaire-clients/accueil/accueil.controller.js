/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('AccueilController', ['$scope', '$location', '$uibModal', 'AccueilServices','EvenementsServices', 'PropertiesServices', 'CommonServices', 'PostITServices',

		function ($scope, $location, $uibModal, AccueilServices, EvenementsServices, PropertiesServices, CommonServices, PostITServices) {

			var controller = this;

			initialiser();
            
			function initialiser() {
                console.log("Init AccueilController");
                reinitialiserErreurs() ;
                $scope.evenementsSemaine=[];
                $scope.evenementsEtPostitsDuJour=[];
                $scope.evtsEtPostitsDuJourBruts=[];
                getEvenementsSemaine();
                getEvenementsEtPostItsDuJour();
			}
            
			function getEvenementsSemaine() {
                controller.erreurGetEvenementsSemaine = undefined;
				controller.requettesHttpEnCours++;                
                
				EvenementsServices.getEvenementsSemaine().then(function(response) {
                    var evenements = response.data;
                    //console.log("getEvenementsSemaine=", evenements);
                    creerEvenementsSemaine(evenements);
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getEvenementsSemaine : ", error);
					controller.erreurGetEvenementsSemaine = {libelle : "Erreur lors de la récupération des évenements de la semaine", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
			function getEvenementsEtPostItsDuJour() {
                controller.erreurGetEvenementsEtPostItsDuJour = undefined;
				controller.requettesHttpEnCours++; 
                
				PostITServices.getPostItsDuJour().then(function(response) {
                    var postits = response.data;
                    
                    //console.log("getPostItsDuJour=", postits);
					controller.requettesHttpEnCours--;
                    
                    // On ajoute les post-its a la liste brute
                    angular.forEach(postits, function(postit, index) {
                        $scope.evtsEtPostitsDuJourBruts.push(postit);
                    });
                    
                    // On recupere ensuite les evenements du jour
                    controller.erreurGetEvenementsEtPostItsDuJour = undefined;
                    controller.requettesHttpEnCours++;                
                    
                    EvenementsServices.getEvenementsDuJour().then(function(response) {
                        var evenements = response.data;
                        controller.requettesHttpEnCours--;
                        
                        // On ajoute les post-its a la liste brute
                        angular.forEach(evenements, function(evenement, index) {
                            $scope.evtsEtPostitsDuJourBruts.push(evenement);
                        });
                        
                        // On formatte les evenements et on les tri par heure
                        formatterEvtEtPostits();
                        
                    }, function (error) {
                        console.error("error getEvenementsSemaine : ", error);
                        controller.erreurGetEvenementsEtPostItsDuJour = {libelle : "Erreur lors de la récupération des évenements du jour", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        controller.requettesHttpEnCours--;
                    });
                    
				}, function (error) {
                    console.error("error erreurGetEvenementsEtPostItsDuJour : ", error);
					controller.erreurGetEvenementsEtPostItsDuJour = {libelle : "Erreur lors de la récupération des post-it du jour", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
            function creerEvenementsSemaine(evenements) {
                angular.forEach(evenements, function(evenement, index) {
                    var evenementSemaine = {};
                    
                    evenementSemaine.nom=convertirEnNomEvenementSemaine(evenement);
                    evenementSemaine.type=evenement.typeEvenement;
                    //console.log("evenementSemaine:",evenementSemaine);
                    
                    // Ajout
                    $scope.evenementsSemaine.push(evenementSemaine);
                });
			}
            
            function convertirEnNomEvenementSemaine(evenement) {
                var debutAsDateObject = CommonServices.convertSlashDDMMYYYYToDateObject(evenement.debut);
                return getDayFromDebutAsDateObject(debutAsDateObject) + " " + debutAsDateObject.getDate() + " à " + evenement.heureDebut.replace(":", "h") + ": " + evenement.nom; 
			}
            
            // Retourne le jour (lundi) à partir de la date (05/05/2018)
            function getDayFromDebutAsDateObject(debutAsDateObject) {
                var day = debutAsDateObject.getDay();
                if (day == 1) {
                    return "Lundi"
                } else if (day == 2) {
                    return "Mardi"
                }else if (day == 3) {
                    return "Mercredi"
                }else if (day == 4) {
                    return "Jeudi"
                }else if (day == 5) {
                    return "Vendredi"
                }else if (day == 6) {
                    return "Samedi"
                }
                return "Dimanche";
            }
            
            function formatterEvtEtPostits() {
                angular.forEach($scope.evtsEtPostitsDuJourBruts, function(evtOuPostitBrut, index) {
                    var evtOuPostitDuJour = {};
                    
                    evtOuPostitDuJour.nom=convertirEnNomDuJour(evtOuPostitBrut);
                    evtOuPostitDuJour.type=evtOuPostitBrut.typeEvenement;
                    evtOuPostitDuJour.heureDebut=evtOuPostitBrut.heureDebut;
                    //console.log("evtOuPostitDuJour:",evtOuPostitDuJour);
                    
                    // Ajout
                    $scope.evenementsEtPostitsDuJour.push(evtOuPostitDuJour);
                });
                
                // tri
                $scope.evenementsEtPostitsDuJour.sort(compareEvtOuPostitParHeure);
			}
            
            function convertirEnNomDuJour(evt) {
                return evt.heureDebut.replace(":", "h") + ": " + evt.nom
			}
            
            function compareEvtOuPostitParHeure(evtA, evtB) {
                
                var timeArrayA = evtA.heureDebut.split(":");
                var timeArrayB = evtB.heureDebut.split(":");
                
                var aHour = parseInt(timeArrayA[0]);
                var bHour = parseInt(timeArrayB[0]);
                var aMinute = parseInt(timeArrayA[1]);
                var bMinute = parseInt(timeArrayB[1]);
                
                // compare hours first
                if (aHour < bHour) return -1;
                if (aHour > bHour) return 1;

                // else aHour === bHour, so compare minutes to break the tie
                if (aMinute < bMinute) return -1;
                if (aMinute > bMinute) return 1;

                // couldn't break the tie
                return 0;
            }
            
            
            
            function reinitialiserErreurs() {
				controller.requettesHttpEnCours = 0;
                controller.erreurGetEvenementsSemaine=undefined;
                controller.erreurGetEvenementsEtPostItsDuJour=undefined;
			}
            
            $scope.redirect = function(path) {
                //console.log("click");
                $location.path(path).search({empty : 'true'}); 
            };
		}
	]);
})();
