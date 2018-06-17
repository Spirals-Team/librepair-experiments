/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('EditionChambreController', ['$scope', '$location', '$routeParams', '$uibModal',
																																	 'ChambreServices', 'EvenementsServices', 
                                                                                                                                     'CommonServices', 'PropertiesServices', 'ClientsServices',
                                                                                                                                    'DevisServices', 

		function ($scope, $location, $routeParams, $uibModal, ChambreServices, EvenementsServices, CommonServices, PropertiesServices, ClientsServices, DevisServices) {

			var controller = this;
            $scope.submitted = false;

            setFormulaireDisabled(false);
            // Recuperer l'id de l'evenement dans le query param
            var idEvenement = $routeParams.idEvenement;
            if (!idEvenement) {
                setFormulaireDisabled(true);
                $scope.erreurEditionEvenement = {libelle : "Erreur lors de la récupération de l'id evenement en query param. Quittez la page.", detail : "Evénement non trouvé", detailChecked : false}
            } else {
                setFormulaireDisabled(false);
            }
            
            var idClient = $routeParams.idClient;
            $scope.infosEvenementEnCoursDeModification = false;
            
			initialiser();
            
            $scope.minDate=new Date();
            
            ////////////////////////////////////////////////////////////////////:

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                console.log("init edition chambre");
                $scope.etatsEvenement = PropertiesServices.getEtatEvenements();
                recupererEvenement(idEvenement);
                recupererDevisDuEvenement(idEvenement);
                recupererClient(idClient);
                setFormulaireDisabled(true);
                $scope.isMontantDisabled = true;
                $scope.isMontantRequired = false;
			}
            
            function recupererClient(idClient) {
                $scope.erreurEditionEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 if (!idClient) {
                        $scope.erreurEditionEvenement = {libelle : "Le client n'a pas d'id de renseigné!"};
                        $scope.requettesHttpEnCours--;
                 } else {
                     console.log("Avant get client");
                    ClientsServices.getClient(idClient).then(function(response) {
                        console.log("response get :", response);
                        $scope.client = response.data;
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get client", error);
                        $scope.erreurEditionEvenement = {libelle : "Erreur lors de la recherche du client " + idClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                    });
                 }
			}
            
            // ---------------------------------
            // Recuperation du client
            // ---------------------------------
            function recupererEvenement(idEvenement) {
                console.log("recup evenement");
                $scope.erreurEditionEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 if (!idEvenement) {
                        $scope.erreurEditionEvenement = {libelle : "L'événement n'a pas d'id de renseigné!"};
                        $scope.requettesHttpEnCours--;
                 } else {
                     console.log("Avant get chambre");
                    ChambreServices.getEvenementById(idEvenement).then(function(response) {
                        console.log("response get :", response);
                        $scope.evenement = response.data;
                        console.log("chambre=", $scope.evenement);
                        formatServerChambreToFront($scope.evenement);
                        
                        // mise a jour couleur
                        angular.forEach($scope.etatsEvenement, function(etat, key) {
                            if (etat.valeur == $scope.evenement.etatEvenement) {
                                $scope.etatEvenementColor = etat.color;
                            }
                        });
                        
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get chambre", error);
                        $scope.erreurEditionEvenement = {libelle : "Erreur lors de la recherche de l'événement " + idEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                        setFormulaireDisabled(true);
                    });
                 }
			}
            
            // ---------------------------------
            // Recuperation des devis du événement
            // ---------------------------------
            function recupererDevisDuEvenement(idEvenement) {
                $scope.erreurGetDevisEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 if (!idEvenement) {
                        $scope.erreurGetDevisEvenement = {libelle : "L'événement n'a pas d'id de renseigné. Quittez la page!"};
                        $scope.requettesHttpEnCours--;
                 } else {
                     console.log("Avant getDevisByEvenementId");
                    DevisServices.getDevisByEvenementId(idEvenement).then(function(response) {
                        console.log("response getDevisByEvenementId :", response);
                        $scope.devis = response.data;
                        console.log("$scope.devis :", $scope.devis);
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur getDevisByEvenementId ", error);
                         $scope.erreurGetDevisEvenement = {libelle : "Erreur lors de la récupération des devis de l'événement " + idEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                    });
                 }
			}
            
             function updateChambre(chambre) {
                $scope.erreurEditionEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 console.log("Avant put");
				ChambreServices.updateEvenement(chambre).then(function(response) {
                    console.log("response update :", response);
					$scope.requettesHttpEnCours--;
                    // Afficher message de mise a jour OK
                    $scope.updateOk=true;
				}, function (error) {
                    console.error("Erreur update ", error);
                    $scope.erreurEditionEvenement = {libelle : "Erreur lors de l'édition de l'evenement " + chambre.numeroEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					$scope.requettesHttpEnCours--;
                    $scope.updateOk=false;
				});
			}
            
            function redirigerPageEditionClient() {
                // La création de l'évenement se fait à partir de cette page,
                // on redirige donc là.
                $location.path("/EditionClient").search({id: idClient}); 
            }
            
            
            // Verification des dispo selon les dates entrées
            $scope.verifierDispo = function() {
                
                $scope.disponibilite={};
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
                $scope.disponibilite={};
            };
            
            $scope.updateColor = function() {
                console.log("updateColor");
                $scope.etatEvenementColor = PropertiesServices.getEtatEvtColorFromValeur($scope.evenement.etatEvenement);
                console.log("color=", $scope.etatEvenementColor);
            };
            
            $scope.clickModification = function(path) {
                console.log("clickModification");
                $scope.submitted = false;
                $scope.infosEvenementEnCoursDeModification = true;
                $scope.evenementToUpdate = angular.copy($scope.evenement);
                $scope.updateOk = false;
                setFormulaireDisabled(false);
            };
            
            $scope.annulerModification = function(path) {
                $scope.submitted = false;
                $scope.infosEvenementEnCoursDeModification = false;
                console.log("annulerModification");
                $scope.evenement = angular.copy($scope.evenementToUpdate);
                $scope.updateOk = false;
                setFormulaireDisabled(true);
            };
            
            $scope.validerModification= function(path) {
                console.log("validerModification");
                if (!$scope.formulaireChambre.$valid) {
                    $scope.submitted = true;
                    $scope.infosEvenementEnCoursDeModification = true;
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : update de la chambre");
                    // mettre a jour seulement si structures differentes?
                    $scope.evenementToUpdate = angular.copy($scope.evenement);
                    formatFrontChambreToServer($scope.evenementToUpdate);
                    updateChambre($scope.evenementToUpdate);
                    $scope.infosEvenementEnCoursDeModification = false;
                    $scope.submitted = true;
                }
                setFormulaireDisabled(!$scope.infosEvenementEnCoursDeModification);
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
            function formatFrontChambreToServer(evenement) {
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
            
            // Dans un service commun!!!!
            function formatServerChambreToFront(evenement) {
                if (evenement.debut) {
                    // Convert date
                    var date_new = CommonServices.convertSlashDDMMYYYYToDateObject(evenement.debut);
                    evenement.debutTmp = date_new;
                    console.log("evenement.debutTmp = ",evenement.debutTmp);
                }
                if (evenement.fin) {
                    // Convert date
                    var date_new = CommonServices.convertSlashDDMMYYYYToDateObject(evenement.fin);
                    evenement.finTmp = date_new;
                    console.log("evenement.debutTmp = ",evenement.finTmp);
                }
                
                var nbDaysBetweenDates = (evenement.finTmp - evenement.debutTmp)  / (1000 * 60 * 60 * 24);
                evenement.nbNuitsTmp = nbDaysBetweenDates + 1;
                
                evenement.heureDebutTmp = new Date(evenement.debutTmp);
                var heurMinuteArray = evenement.heureDebut.split(":");
                evenement.heureDebutTmp.setHours(heurMinuteArray[0], heurMinuteArray[1]);
            }
            
            function addDays(date, days) {
              var result = new Date(date);
              result.setDate(result.getDate() + days);
              return result;
            }
            
		}
	]);
})();
