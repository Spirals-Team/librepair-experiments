/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('CalendrierController', ['$scope', '$ocLazyLoad', '$window','$uibModal',
																																	 'CalendrierServices','moment', 'calendarConfig','calendarEventTitle', 'EvenementsServices', 'CommonServices', 'PostITServices', 

                                                                                                                                        

		function ($scope, $ocLazyLoad, $window, $uibModal, CalendrierServices,moment, calendarConfig, calendarEventTitle, EvenementsServices, CommonServices, PostITServices) {
    
            // ORIGINAL
            var controller = this;
            
			initialiser();
            
			function initialiser() {
                console.log("Init CalendrierController");
				toutRafraichir();
			}

            controller.calendarView = 'month';
            controller.viewDate = moment().startOf('month').toDate();
            $scope.viewDate = controller.viewDate;
            $scope.currentCalendarDate=new Date();            
            
            calendarConfig.dateFormatter = 'moment'; // use moment instead of angular for formatting dates
            calendarConfig.allDateFormats.moment.date.hour = 'HH:mm'; // heure sur 24h dans le jour
            var originali18n = angular.copy(calendarConfig.i18nStrings);
            calendarConfig.i18nStrings.weekNumber = 'Semaine {week}';

            $window.moment = $window.moment || moment;
            $ocLazyLoad.load('locales/locales_french.js').then(function() {
              moment.locale('fr', {
                week: {
                  dow: 1 // Monday is the first day of the week
                }
              });
            });

            var originalEventTitle = angular.copy(calendarEventTitle);

            //////////////////////////////////////////////////////////////////
            
			function getEvenements() {
                controller.erreurGetEvenements = undefined;
				controller.requettesHttpEnCours++;
				EvenementsServices.getEvenements().then(function(response) {
                    var evenements = response.data;
                    
                    // Creer evenements
                    creerEvenementsCalendrier(evenements);

					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getEvenements : ", error);
					controller.erreurGetEvenements = {libelle : "Erreur lors de la récupération des évenements", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
			function getPostIts() {
                controller.erreurGetEvenements = undefined;
				controller.requettesHttpEnCours++;
				PostITServices.getPostIts().then(function(response) {
                    var postits = response.data;
                    console.log("postits=", postits);
                    
                    // Creer evenements
                    creerPostItCalendrier(postits);

                    //~ console.log("date:", date);
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getEvenements : ", error);
					controller.erreurGetEvenements = {libelle : "Erreur lors de la récupération des post-it", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
             function update(postit) {
                controller.erreurGetEvenements = undefined;
				controller.requettesHttpEnCours++;
                 console.log("Avant put");
				PostITServices.update(postit).then(function(response) {
                    console.log("response update :", response);
                    toutRafraichir();
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("Erreur update ", error);
                    controller.erreurGetEvenements = {libelle : "Erreur lors de l'édition du post-it " , detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
             function remove(id) {
                controller.erreurGetEvenements = undefined;
				controller.requettesHttpEnCours++;
                 console.log("Avant delete");
				PostITServices.remove(id).then(function(response) {
                    console.log("response delete :", response);
                     toutRafraichir();
					controller.requettesHttpEnCours--;
				}, function (error) {
                    console.error("Erreur delete ", error);
                    controller.erreurGetEvenements = {libelle : "Erreur lors du delete du post-it " , detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
			}
            
			function creerPostItCalendrier(postits) {
                console.log("creerPostItCalendrier");
                angular.forEach(postits, function(postit, index) {
                    console.log("creerPostItCalendrier forEach");
                    var postitsCalendrier = {};
                    
                    postitsCalendrier.title=postit.nom;
                    postitsCalendrier.color= convertirTypeEnCouleur("POST-IT");
                    postitsCalendrier.startsAt=convertDateToCalendarDate(postit.debut, postit.heureDebut);
                    postitsCalendrier.endsAt=convertDateToCalendarDate(postit.debut, postit.heureDebut);
                    postitsCalendrier.type="POST-IT";
                    postitsCalendrier.id=postit.id;
                    postitsCalendrier.actions = [{
                      label: '<i class=\'glyphicon glyphicon-pencil\' style=\'color:#6699ff\'></i>', // the label of the action
                      onClick: editPostIt
                    },{
                      label: '<i class=\'glyphicon glyphicon-remove\' style=\'color:#ff4d4d\'></i>', // the label of the action
                      onClick: removePostIt
                    }];
                   postitsCalendrier.incrementsBadgeTotal = false; // evite d'afficher les post-it dans les mois
                    
                    // Ajout du post-it au calendrier
                    controller.events.push(postitsCalendrier);
                });
			}

            
            function editPostIt(args) {
                console.log('editPostIt', args.calendarEvent);
                
                var id = args.calendarEvent.id;
                console.log("edit post-it:",id); 
                
                controller.erreurGetEvenements = undefined;
				controller.requettesHttpEnCours++;
                 console.log("Avant edit");
				PostITServices.getPostItById(id).then(function(response) {
                    console.log("response get by id :", response.data);
					controller.requettesHttpEnCours--;
                    
                    modifierPostIt(response.data);                    
                    
				}, function (error) {
                    console.error("Erreur update ", error);
                    controller.erreurGetEvenements = {libelle : "Erreur lors dela recuperation du post-it " , detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
				});
            }
            
			function creerEvenementsCalendrier(evenements) {
                
                angular.forEach(evenements, function(evenement, index) {
                    var evenementCalendrier = {};
                    
                    evenementCalendrier.title=convertirTypeEvenementPourNom(evenement.typeEvenement) + evenement.nom;
                    evenementCalendrier.color= convertirTypeEnCouleur(evenement.typeEvenement);
                    evenementCalendrier.startsAt=convertDateToCalendarDate(evenement.debut, evenement.heureDebut);
                    evenementCalendrier.endsAt=convertDateToCalendarDate(evenement.fin, evenement.heureFin);
                    evenementCalendrier.type=evenement.typeEvenement;
                    
                    // Ajout de l'evenement au calendrier
                    controller.events.push(evenementCalendrier);
                });
			}
            
            function convertirTypeEnCouleur(type) {
                var primaryColor = '#e3bc08' // jaune
                var secondaryColor = '#ffe066' // jaune clair
                if (type == 'LOCATION_SALLE') {
                    primaryColor = '#D50000' // rouge
                    secondaryColor = '#ff6666' // rouge
                } else if (type == 'MARIAGE') {
                    primaryColor = '#0277BD' // bleu
                    secondaryColor = '#4da6ff' // bleu
                }else if (type == 'REPAS') {
                    primaryColor = '#4CAF50' // vert
                    secondaryColor = '#66cc99' // vert
                }else if (type == 'CHAMBRE') {
                    primaryColor = '#AA00FF' // violet
                    secondaryColor = '#c299ff' // violet
                }else if (type == 'LIBERTE') {
                    primaryColor = '#8D6E63' // marron
                    secondaryColor = '#e6ccb3' // violet
                } else if (type == 'POST-IT') {
                    primaryColor = '#e3bc08' // jaune
                    secondaryColor = '#ffe066' // jaune clair
                }
                
                var couleur = {
                      primary: primaryColor,//, // the primary event color (should be darker than secondary)
                      secondary: secondaryColor // the secondary event color (should be lighter than primary)
                };
                return couleur;
            }

            function convertirTypeEvenementPourNom(type) {
                var nom = "[Inconnu] "
                if (type == 'LOCATION_SALLE') {
                    nom = "[Location] "
                } else if (type == 'MARIAGE') {
                    nom = "[Mariage] "
                }else if (type == 'REPAS') {
                    nom = "[Repas] "
                }else if (type == 'CHAMBRE') {
                    nom = "[Chambre] "
                }else if (type == 'LIBERTE') {
                    nom = "[Liberté] "
                }
                return nom;
            }
            
			function toutRafraichir() {
				controller.requettesHttpEnCours = 0;
                controller.erreurGetEvenements = undefined;
                controller.events = [];
                controller.evenements = [];
                controller.postits = [];
                getEvenements();
                getPostIts();
			}
            
            function modifierPostIt(postit) {
                console.log("modifierPostIt : ", postit);
                
                $scope.postit = {};
                $scope.postit.evenement = postit;
                
                console.log("$scope.postit.evenement = ", $scope.postit.evenement);
                
                $uibModal.open({
                    templateUrl: 'gestionnaire-clients/calendrier/post-it/edition-post-it.html',
                    controller: 'EditionPostITController',
                    scope: $scope,
                    resolve: { postit: function () { return $scope.postit; } }
                })
                .result.then(function() {
                    console.log("Validation");
                    
                    // FIXME : voir si y'a pas mieux que de tout rafraichir (je crois pas � cause de l'asynchronisme des reponses)
                    toutRafraichir();
                    
                }, function() {
                    console.log("ferme fenetre");
                });
                
                // Reset
                $scope.postit={};

            };
            
            function removePostIt(args) {
                var id = args.calendarEvent.id;
                console.log("removePostIt : ", id);
                
                $uibModal.open({
                    templateUrl: 'gestionnaire-clients/calendrier/post-it/suppression-post-it.html',
                    })
                .result.then(function() {
                    console.log("Validation");
                    
                    remove(id);
                    
                    // FIXME : voir si y'a pas mieux que de tout rafraichir (je crois pas � cause de l'asynchronisme des reponses)
                    toutRafraichir();
                    
                }, function() {
                    console.log("ferme fenetre");
                });

            };

            controller.viewChangeClicked = function(date, nextView) {
                $scope.currentCalendarDate=date;
                console.log(date, nextView);
                return true;
            };
            
            // required so other demos work as before
            $scope.$on('$destroy', function() {
              angular.extend(calendarEventTitle, originalEventTitle);
            });
            
            $scope.clicPreviousNext = function() {
                $scope.currentCalendarDate=controller.viewDate;
                console.log("viewDate=", controller.viewDate);
            }
            
            $scope.creerPostIt = function() {
                console.log("creerPostIt");
                
                $scope.postit={};
                
                $uibModal.open({
                    templateUrl: 'gestionnaire-clients/calendrier/post-it/post-it.html',
                    controller: 'PostITController',
                    scope: $scope
                })
                .result.then(function() {
                    console.log("Validation");
                    
                    // FIXME : voir si y'a pas mieux que de tout rafraichir (je crois pas � cause de l'asynchronisme des reponses)
                    toutRafraichir();
                    
                }, function() {
                    console.log("ferme fenetre");
                });
                
                // Reset
                $scope.postit={};

            };
            
            // Dans un service commun!!!!
            function formatServerPostitToFront(postit) {
                if (postit.debut) {
                    // Convert date
                    var date_new = CommonServices.convertSlashDDMMYYYYToDateObject(postit.debut);
                    postit.debutTmp = date_new;
                    console.log("postit.debutTmp = ",postit.debutTmp);
                }
                if (postit.fin) {
                    // Convert date
                    var date_new = CommonServices.convertSlashDDMMYYYYToDateObject(postit.fin);
                    postit.finTmp = date_new;
                    console.log("evenement.debutTmp = ",evenement.finTmp);
                }
            }
            
            $scope.timespanClicked = function(event, date){
                $scope.currentCalendarDate=date;
                console.log("$scope.currentCalendarDate=", $scope.currentCalendarDate);
            };
            
            function convertDateToCalendarDate(inputDate, inputTime) {
                var dateArray = inputDate.split("/");
                var convertedDate = new Date(dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0] + " " + inputTime + ":00")
                return convertedDate;
            }

		}
	]);
        
})();
