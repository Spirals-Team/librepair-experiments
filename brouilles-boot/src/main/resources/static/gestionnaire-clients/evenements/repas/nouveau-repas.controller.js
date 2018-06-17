/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('NouveauRepasController', ['$scope', '$location', '$routeParams', '$uibModal',
																																	 'RepasServices', 'EvenementsServices', 'CommonServices', 'PropertiesServices', 'ClientsServices',

		function ($scope, $location, $routeParams, $uibModal, RepasServices, EvenementsServices, CommonServices, PropertiesServices, ClientsServices) {

			var controller = this;
            $scope.submitted = false;
            
            $scope.evenement={typeEvenement:'REPAS',
                                            heureDebut : '12:00',
                                            heureFin : '23:00',
                                            prix1 : 0,
                                            prix2 : 0,
                                            prixLocationSalle : 0,
                                            prixAlcool : 0,
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
            
            // Date d'aujourdhui au format yyyy-mm-dd
            var today = CommonServices.formatDateToYYYYMMDD(now);
            $scope.minDate=new Date();
            
            ////////////////////////////////////////////////////////////////////:
            
            initialiser();

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                $scope.isMenuMidiActif=false;
                $scope.isMenuSoirActif=false;
                updateLocationSalle();
                recupererClient(idClient);
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
                        $scope.erreurPostEvenement = {libelle : "Erreur lors  de la recherche du client " + idClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                    });
                 }
			}
            
             function postEvenement(evenement) {
                $scope.erreurPostEvenement = undefined;
				$scope.requettesHttpEnCours++;
                 
                console.log("Post de l'evenement:", evenement);
                 
				RepasServices.postEvenement(evenement).then(function(response) {
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
            
            $scope.updateColor = function() {
                $scope.etatEvenementColor = PropertiesServices.getEtatEvtColorFromValeur($scope.evenement.etatEvenement);
            };
            
            $scope.annulerEvenement = function() {
                console.log("annulerEvenement");
                redirigerPageEditionClient();
            };
            
            $scope.validerEvenement = function() {
                console.log("validerEvenement");
                $scope.submitted = true;
                if (!midiOuSoirChecked() ){
                    console.log("Formulaire non valide car midiOuSoirChecked est false");
                }
                else if (!$scope.formulaireEvenement.$valid) {
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : creation de l'évenement");
                    updateChampRepasSelection();
                    convertDate($scope.evenement);
                    postEvenement($scope.evenement);
                }
            };
            
            // Verification des dispo selon les dates entr�es
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
            
            function updateChampRepasSelection() {
                var repasLeMidi = $scope.evenement.midiTmp;
                var repasLeSoir = $scope.evenement.soirTmp;
                
                if (repasLeMidi && repasLeSoir) {
                    $scope.evenement.repasSelection = "MIDI_ET_SOIR";
                    $scope.evenement.heureDebut="12:00";
                    $scope.evenement.heureFin="23:00";
                } else if (repasLeMidi) {
                    $scope.evenement.repasSelection = "MIDI";
                    $scope.evenement.heureDebut="12:00";
                    $scope.evenement.heureFin="15:00";
                } else {
                    $scope.evenement.repasSelection = "SOIR";
                    $scope.evenement.heureDebut="19:00";
                    $scope.evenement.heureFin="23:00";
                }
            }
    
            
            // Dans un service commun!!!!
            function convertDate(evenement) {
                if (evenement.debutTmp) {
                    // Convert date
                    var date_new = new Date($scope.evenement.debutTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.debut = date_new;
                    console.log("evenement.debut = ",evenement.debut);
                }
                if (evenement.finTmp) {
                    // Convert date
                    // ON MET LA MEME DATE POUR DEBUT ET FIN
                    var date_new = new Date($scope.evenement.debutTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.fin = date_new;
                    console.log("evenement.fin = ",evenement.fin);
                }
            }
            
		}
	]);
})();
