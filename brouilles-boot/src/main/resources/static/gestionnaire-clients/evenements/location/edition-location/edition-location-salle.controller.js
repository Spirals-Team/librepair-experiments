/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('EditionLocationController', ['$scope', '$location', '$routeParams', '$uibModal',
																																	 'LocationSalleServices', 'EvenementsServices', 'CommonServices', 'PropertiesServices', 'ClientsServices', 'DevisServices', 

		function ($scope, $location, $routeParams, $uibModal, LocationSalleServices, EvenementsServices, CommonServices, PropertiesServices, ClientsServices, DevisServices) {

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
                console.log("init edition location");
                $scope.etatsEvenement = PropertiesServices.getEtatEvenements();
                $scope.typesTables = PropertiesServices.getTypesTables();
                recupererEvenement(idEvenement);
                recupererDevisDuEvenement(idEvenement);
                recupererClient(idClient);
                setFormulaireDisabled(true);
			}
            
            function redirigerPageEditionClient() {
                // La création de l'évenement se fait à partir de cette page,
                // on redirige donc là.
                $location.path("/EditionClient").search({id: idClient}); 
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
                     console.log("Avant get mariage");
                    LocationSalleServices.getEvenementById(idEvenement).then(function(response) {
                        console.log("response get :", response);
                        $scope.evenement = response.data;
                        console.log("location=", $scope.evenement);
                        
                        formatServerLocationToFront($scope.evenement);
                        
                        // mise a jour couleur
                        angular.forEach($scope.etatsEvenement, function(etat, key) {
                            if (etat.valeur == $scope.evenement.etatEvenement) {
                                $scope.etatEvenementColor = etat.color;
                            }
                        });
                        
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get location", error);
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
            
             function updateLocation(location) {
                $scope.erreurEditionEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 console.log("Avant put");
				LocationSalleServices.updateEvenement(location).then(function(response) {
                    console.log("response update :", response);
					$scope.requettesHttpEnCours--;
                    // Afficher message de mise à  jour OK
                    $scope.updateOk=true;
				}, function (error) {
                    console.error("Erreur update ", error);
                    $scope.erreurEditionEvenement = {libelle : "Erreur lors de l'édition de l'evenement " + location.numeroEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					$scope.requettesHttpEnCours--;
                    $scope.updateOk=false;
				});
			}
            
            $scope.updateColor = function() {
                $scope.etatEvenementColor = PropertiesServices.getEtatEvtColorFromValeur($scope.evenement.etatEvenement);
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
                if (!$scope.formulaireLocation.$valid) {
                    $scope.submitted = true;
                    $scope.infosEvenementEnCoursDeModification = true;
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : update de la location");
                    // mettre a jour seulement si structures differentes?
                    $scope.evenementToUpdate = angular.copy($scope.evenement);
                    formatFrontLocationToServer($scope.evenementToUpdate);
                    updateLocation($scope.evenementToUpdate);
                    $scope.infosEvenementEnCoursDeModification = false;
                    $scope.submitted = true;
                }
                setFormulaireDisabled(!$scope.infosEvenementEnCoursDeModification);
            };
            
            // Verification des dispo selon les dates entrées
            $scope.verifierDispo = function() {
                console.log("verifierDispo");
                
                $scope.disponibilite={};
                
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
            $scope.dateDebutChanged = function() {
                $scope.evenement.finTmp = $scope.evenement.debutTmp;
            };
            
            function setFormulaireDisabled(disabled) {
                $scope.formDisabled=disabled;
            }    
            
            // Dans un service commun!!!!
            function formatFrontLocationToServer(evenement) {
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
            }
            
            // Dans un service commun!!!!
            function formatServerLocationToFront(evenement) {
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
            }
            
		}
	]);
})();
