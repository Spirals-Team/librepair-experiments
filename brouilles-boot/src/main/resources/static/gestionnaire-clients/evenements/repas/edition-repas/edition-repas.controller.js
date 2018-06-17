/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('EditionRepasController', ['$scope', '$location', '$routeParams', '$uibModal',
																																	 'RepasServices', 'EvenementsServices', 'CommonServices', 'PropertiesServices', 'ClientsServices',

		function ($scope, $location, $routeParams, $uibModal, RepasServices, EvenementsServices, CommonServices, PropertiesServices, ClientsServices) {

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
            
            // Date d'aujourdhui au format yyyy-mm-dd
            $scope.minDate=new Date();
            
            ////////////////////////////////////////////////////////////////////:

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                $scope.isMenuMidiActif=false;
                $scope.isMenuSoirActif=false;
                $scope.etatsEvenement = PropertiesServices.getEtatEvenements();
                recupererEvenement(idEvenement);
                recupererClient(idClient);
                setFormulaireDisabled(true);
			}
            
            // ---------------------------------
            // Recuperation du client
            // ---------------------------------
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
                     console.log("Avant get repas");
                    RepasServices.getEvenementById(idEvenement).then(function(response) {
                        console.log("response get :", response);
                        $scope.evenement = response.data;
                        console.log("repas=", $scope.evenement);
                        formatServerRepasToFront($scope.evenement);
                        
                        // mise a jour couleur
                        angular.forEach($scope.etatsEvenement, function(etat, key) {
                            if (etat.valeur == $scope.evenement.etatEvenement) {
                                $scope.etatEvenementColor = etat.color;
                            }
                        });
                        
                        updateLocationSalle();
                        
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get repas", error);
                        $scope.erreurEditionEvenement = {libelle : "Erreur lors de la recherche de l'événement " + idEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                        setFormulaireDisabled(true);
                    });
                 }
			}
            
             function updateRepas(repas) {
                $scope.erreurEditionEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 console.log("Avant put");
				RepasServices.updateEvenement(repas).then(function(response) {
                    console.log("response update :", response);
					$scope.requettesHttpEnCours--;
                    // Afficher message de mise Ã  jour OK
                    $scope.updateOk=true;
				}, function (error) {
                    console.error("Erreur update ", error);
                    $scope.erreurEditionEvenement = {libelle : "Erreur lors de l'édition de l'evenement " + repas.numeroEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
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
                $scope.submitted = true;
                if (!midiOuSoirChecked() ){
                    console.log("Formulaire non valide car midiOuSoirChecked est false");
                }
                else if (!$scope.formulaireEvenement.$valid) {
                    console.log("Formulaire non valide");
                }  else {
                    console.log("Formulaire valide : update du repas");
                    $scope.evenementToUpdate = angular.copy($scope.evenement);
                    formatFrontRepasToServer($scope.evenementToUpdate);
                    updateRepas($scope.evenementToUpdate);
                    $scope.infosEvenementEnCoursDeModification = false;
                    $scope.submitted = true;
                }
                setFormulaireDisabled(!$scope.infosEvenementEnCoursDeModification);
            };
            
            // Verification des dispo selon les dates entrées
            $scope.verifierDispo = function() {
                console.log("verifierDispo");
                
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
            $scope.dateDebutChanged = function() {
                $scope.evenement.finTmp = $scope.evenement.debutTmp;
            };
            
            $scope.clickMenuMidi = function() {
                $scope.isMenuMidiActif=!$scope.isMenuMidiActif
                
                if (!$scope.isMenuMidiActif) {
                    $scope.evenement.prix1=0;
                    $scope.evenement.menu1="";
                }
            };
            
            $scope.clickMenuSoir = function() {
                $scope.isMenuSoirActif=!$scope.isMenuSoirActif;
                
                if (!$scope.isMenuSoirActif) {
                    console.log("!actif");
                    $scope.evenement.prix2=0;
                    $scope.evenement.menu2="";
                }
            };
            
            $scope.clickLocationSalle = function() {
                updateLocationSalle();
            };
            
            function updateLocationSalle() {
                var locationSalleChecked = $scope.evenement.locationSalle;
                if(locationSalleChecked) {
                    $scope.prixLocationSalleRequired=true;
                    $scope.prixLocationSalleDisabled=false;
                } else {
                    $scope.prixLocationSalleRequired=false;
                    $scope.prixLocationSalleDisabled=true;
                    $scope.evenement.prixLocationSalle=0;
                }
            }
            
            function setFormulaireDisabled(disabled) {
                $scope.formDisabled=disabled;
            }
            
            function midiOuSoirChecked() {
                return $scope.isMenuMidiActif || $scope.isMenuSoirActif; 
            }
            
            function updateChampRepasSelection(evenement) {
                var repasLeMidi = evenement.midiTmp;
                var repasLeSoir = evenement.soirTmp;
                
                console.log("repasLeMidi:", repasLeMidi);
                console.log("repasLeSoir:", repasLeSoir);
                
                if (repasLeMidi && repasLeSoir) {
                    evenement.repasSelection = "MIDI_ET_SOIR";
                    evenement.heureDebut="12:00";
                    evenement.heureFin="23:00";
                } else if (repasLeMidi) {
                    evenement.repasSelection = "MIDI";
                    evenement.heureDebut="12:00";
                    evenement.heureFin="15:00";
                } else {
                    evenement.repasSelection = "SOIR";
                    evenement.heureDebut="19:00";
                    evenement.heureFin="23:00";
                }
            }
    
            
            // Dans un service commun!!!!
            function formatFrontRepasToServer(evenement) {
                
                updateChampRepasSelection(evenement);
                
                if (evenement.debutTmp) {
                    // Convert date
                    var date_new = new Date(evenement.debutTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.debut = date_new;
                    console.log("evenement.debut = ",evenement.debut);
                }
                if (evenement.finTmp) {
                    // Convert date
                    // ON MET LA MEME DATE POUR DEBUT ET FIN
                    var date_new = new Date(evenement.debutTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.fin = date_new;
                    console.log("evenement.fin = ",evenement.fin);
                }
            }
            
            // Dans un service commun!!!!
            function formatServerRepasToFront(evenement) {
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
                
                if (evenement.repasSelection == "MIDI_ET_SOIR") {
                    evenement.midiTmp = true;
                    evenement.soirTmp = true;
                    $scope.isMenuMidiActif = true;
                    $scope.isMenuSoirActif = true;
                } else if (evenement.repasSelection == "MIDI") {
                    evenement.midiTmp = true;
                    evenement.soirTmp = false;
                    $scope.isMenuMidiActif = true;
                    $scope.isMenuSoirActif = false;
                }else if (evenement.repasSelection == "SOIR") {
                    evenement.midiTmp = false;
                    evenement.soirTmp = true;
                    $scope.isMenuMidiActif = false;
                    $scope.isMenuSoirActif = true;
                }
                
                $scope.prixLocationSalleDisabled = true;
                if(evenement.locationSalle) {
                    $scope.prixLocationSalleDisabled = false;
                }
            }
            
		}
	]);
})();
