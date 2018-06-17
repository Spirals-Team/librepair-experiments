/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('NouveauMariageController', ['$scope', '$location', '$routeParams', '$uibModal',
																																	 'MariageServices', 'EvenementsServices', 'CommonServices', 'PropertiesServices', 'ClientsServices',

		function ($scope, $location, $routeParams, $uibModal, MariageServices, EvenementsServices, CommonServices, PropertiesServices, ClientsServices) {

			var controller = this;
            $scope.submitted = false;
            
            $scope.evenement={
                                            typeEvenement:'MARIAGE',
                                            heureDebut : '12:00',
                                            heureFin : '23:00',
                                            etatEvenement : 'PRE_RESERVE'
                                        };
            $scope.etatsEvenement = PropertiesServices.getEtatEvenements();
            $scope.etatEvenementColor = $scope.etatsEvenement[0].color;

            setFormulaireDisabled(false);
            // Recuperer l'id du client dans le query param
            var idClient = $routeParams.idClient;
            if (!idClient) {
                // TODO : disable le formulaire, on peut rien faire sans le client
                setFormulaireDisabled(true);
                $scope.erreurPostEvenement = {libelle : "Erreur lors de la récupération de l'id client en query param. Quittez la page.", detail : "Non trouvé", detailChecked : false}
                console.log("error!!!!");
            } else {
                // On set l'id client! Indispensable.
                setFormulaireDisabled(false);
                $scope.evenement.idClient=idClient;
            }
            
			initialiser();
                                
            
            // Init nb personnes et date
            var now = new Date();
            $scope.evenement.nbPersonnesRepas=1;
            $scope.evenement.nbPersonnesCocktail=1;
            $scope.evenement.debutTmp= now
            $scope.evenement.finTmp= new Date(now);
            
            $scope.minDate=new Date();
            
            ////////////////////////////////////////////////////////////////////:

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                recupererClient(idClient);
                $scope.avecBrunchDisabled=true;
			}
            
            function redirigerPageEditionClient() {
                // La création de l'évenement se fait à partir de cette page,
                // on redirige donc là.
                $location.path("/EditionClient").search({id: idClient}); 
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
            
             function postEvenement(evenement) {
                $scope.erreurPostEvenement = undefined;
				$scope.requettesHttpEnCours++;

                console.log("Avant Post de l'evenement:", evenement);
                convertEvenementFrontToServer(evenement);
                 
                console.log("Post de l'evenement:", evenement);
                 
				MariageServices.postEvenement(evenement).then(function(response) {
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
            
            // On a qu'une date, donc fin = debut
            $scope.dateDebutOuLendemainChanged = function() {
                if ($scope.evenement.debutTmp) {
                    if ($scope.evenement.avecLendemain) {
                        $scope.evenement.finTmp.setDate($scope.evenement.debutTmp.getDate()+1);
                        $scope.avecBrunchDisabled=false;
                        $scope.evenement.heureFin = '13:00';
                    } else {
                        $scope.avecBrunchDisabled=true;
                        $scope.evenement.finTmp = angular.copy($scope.evenement.debutTmp);
                        $scope.evenement.heureFin = '23:00';
                        $scope.evenement.avecBrunch = false;
                    }
                }                
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
                console.log("$scope", $scope);
                if (!$scope.formulaireMariage.$valid) {
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : creation de l'évenement");
                    postEvenement($scope.evenement);
                }
            };
            
            function setFormulaireDisabled(disabled) {
                $scope.formDisabled=disabled;
            }
            
            // Dans un service commun!!!!
            function convertEvenementFrontToServer(evenement) {
                if (evenement.debutTmp) {
                    // Convert date
                    var date_new = new Date($scope.evenement.debutTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.debut = date_new;
                    console.log("evenement.debut = ",evenement.debut);
                }
                if (evenement.finTmp) {
                    // Convert date
                    var date_new = new Date($scope.evenement.finTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.fin = date_new;
                    console.log("evenement.fin = ",evenement.fin);
                }
                if (evenement.heureDebutTmp) {
                    // Convert date
                    var date_new = new Date($scope.evenement.heureDebutTmp);
                    var time_new= date_new.toLocaleTimeString();
                    evenement.heureDebut = time_new.substring(0,5);
                    console.log("evenement.heureDebut = ",evenement.heureDebut);
                }
                
                $scope.evenement.nbPersonnes = $scope.evenement.nbPersonnesRepas;
            }
            
		}
	]);
})();
