/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('NouvelleChambreController', ['$scope', '$location', '$routeParams', '$uibModal',
																																	 'ChambreServices', 'EvenementsServices', 
                                                                                                                                     'CommonServices', 'PropertiesServices', 'ClientsServices',

		function ($scope, $location, $routeParams, $uibModal, ChambreServices, EvenementsServices, CommonServices, PropertiesServices, ClientsServices) {

			var controller = this;
            $scope.submitted = false;
            
            $scope.evenement={typeEvenement:'CHAMBRE', montant:0, etatEvenement : 'PRE_RESERVE'};
            $scope.etatsEvenement = PropertiesServices.getEtatEvenements();
            $scope.etatEvenementColor = $scope.etatsEvenement[0].color;
                                
            setFormulaireDisabled(false);
            // Recuperer l'id du client dans le query param
            var idClient = $routeParams.idClient;
            if (!idClient) {
                // TODO : disable le formulaire, on peut rien faire sans le client
                setFormulaireDisabled(true);
                $scope.erreurPostEvenement = {libelle : "Erreur lors de la récupération de l'id client en query param. Quittez la page.", detail : "Non trouvé", detailChecked : false}
            } else {
                // On set l'id client! Indispensable.
                setFormulaireDisabled(false);
                $scope.evenement.idClient=idClient;
            }
            
            // Init nb personnes et date
            var now = new Date();
            $scope.evenement.nbPersonnes=1;
            $scope.evenement.debutTmp= now;
            $scope.evenement.finTmp= new Date(now);
            $scope.evenement.nbNuitsTmp=1;
            
            $scope.minDate=new Date();
            ////////////////////////////////////////////////////////////////////:
            
            initialiser();

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                recupererClient(idClient);
                $scope.isMontantDisabled = true;
                $scope.isMontantRequired = false;
			}
            
            // ---------------------------------
            // Recuperation du client
            // ---------------------------------
            function recupererClient(idClient) {
                $scope.erreurPostEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 if (!idClient) {
                        $scope.erreurPostEvenement = {libelle : "Le client n'a pas d'id de renseigné!"};
                        $scope.requettesHttpEnCours--;
                 } else {
                     console.log("Avant get client");
                    ClientsServices.getClient(idClient).then(function(response) {
                        console.log("response get :", response);
                        $scope.client = response.data;
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get client", error);
                        $scope.erreurPostEvenement = {libelle : "Erreur lors de la recherche du client " + idClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                    });
                 }
			}
            
            function redirigerPageEditionClient() {
                // La création de l'évenement se fait à partir de cette page,
                // on redirige donc là.
                $location.path("/EditionClient").search({id: idClient}); 
            }
            
             function postEvenement(evenement) {
                $scope.erreurPostEvenement = undefined;
				$scope.requettesHttpEnCours++;

                console.log("Avant Post de l'evenement:", evenement);
                convertDate(evenement);
                 
                console.log("Post de l'evenement:", evenement);
                 
				ChambreServices.postEvenement(evenement).then(function(response) {
                    console.log("response post :", response);
					$scope.requettesHttpEnCours--;
                    
                    // Appel OK : on redirige vers la page d'édition du client
                    redirigerPageEditionClient();
				}, function (error) {
                    console.error("Erreur post ", error);
                    $scope.erreurPostEvenement = {libelle : "Erreur lors de la creation de l'événement ", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                    $scope.requettesHttpEnCours--;
				});
			}
            
            // Verification des dispo selon les dates entrées
            $scope.verifierDispo = function() {
                
                $scope.disponibilite={mariageLendemain : false};
                $scope.evenement.finTmp = addDays($scope.evenement.debutTmp,$scope.evenement.nbNuitsTmp - 1);
                
                $uibModal.open({
                    templateUrl: 'gestionnaire-clients/evenements/disponibilite/disponibilite-evenement.html',
                    controller: 'DisponibiliteEvenementController',
                    windowClass: 'app-modal-new-customer',
                    scope: $scope
                })
                .result.then(function() {
                    // L'utilisateur valide
                    console.log("Validation");
                    
                }, function() {
                    // L'utilisateur ferme la fenetre.
                    console.log("ferme fenetre");
                });
                
                // Reset
                $scope.disponibilite={mariageLendemain : false};
            };
            
            $scope.updateColor = function() {
                console.log("updateColor");
                $scope.etatEvenementColor = PropertiesServices.getEtatEvtColorFromValeur($scope.evenement.etatEvenement);
                console.log("color=", $scope.etatEvenementColor);
            };
            
            $scope.annulerEvenement = function() {
                console.log("annulerEvenement");
                redirigerPageEditionClient();
            };
            
            $scope.validerEvenement = function() {
                console.log("validerEvenement");
                $scope.submitted = true;
                if (!$scope.formulaireChambre.$valid) {
                    console.log("Formulaire non valide");
                } else {
                    postEvenement($scope.evenement);
                }
            };
            
            $scope.clickAcompte = function() {                
                if ($scope.evenement.avecAcompte) {
                    $scope.isMontantDisabled=false;
                    $scope.isMontantRequired=true;
                }else {
                    $scope.isMontantDisabled=true;
                    $scope.isMontantRequired=false;
                    $scope.evenement.montant=0;
                }
            }
            
            $scope.debutDateChanged = function(event) {
                console.log("event:",event);
            }
            
            function setFormulaireDisabled(disabled) {
                $scope.formDisabled = disabled;
            }
            
            // Dans un service commun!!!!
            function convertDate(evenement) {
                if (evenement.debutTmp) {
                    // Convert date
                    var date_new = new Date(evenement.debutTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.debut = date_new;
                    console.log("evenement.debut = ",evenement.debut);
                }
                if (evenement.heureDebutTmp) {
                    // Convert date
                    var date_new = new Date($scope.evenement.heureDebutTmp);
                    var time_new= date_new.toLocaleTimeString();
                    evenement.heureDebut = time_new.substring(0,5);
                    console.log("evenement.heureDebut = ",evenement.heureDebut);
                }
                
                evenement.fin = addDays(evenement.debutTmp,evenement.nbNuitsTmp - 1);
                evenement.fin= evenement.fin.toLocaleDateString();
                console.log("evenement.fin = ",evenement.fin);
                
                // Permet d'afficher correctement dans le calendrier,
                // pour avoir une date fin > date debut si une seule journee
                evenement.heureFin='23:00';
            }
            
            function addDays(date, days) {
              var result = new Date(date);
              result.setDate(result.getDate() + days);
              return result;
            }
            
		}
	]);
})();
