/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('EditionClientController', ['$scope', '$location', '$routeParams',
																																	 'EvenementsServices', 'ClientsServices', 'PropertiesServices',
                                                                                                                                    'DevisServices',

		function ($scope, $location, $routeParams, EvenementsServices, ClientsServices, PropertiesServices, DevisServices) {

			var controller = this;
            $scope.submitted = false;
            
            $scope.client={};
            $scope.clientToUpdate = {};

            $scope.categories = PropertiesServices.getCategories();
            $scope.typeEvenements = PropertiesServices.getTypeEvenements();

            $scope.typeEvenement = $scope.typeEvenements[0].valeur;
                
            $scope.infosClientEnCoursDeModification = false;
            var idClient = $routeParams.id;
			initialiser();

            // TODO : reinitialiser les erreurs a chaque fois!
			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                controller.erreurEditionClient = undefined;
                controller.erreurGetEvenementsDuClient = undefined;
                controller.erreurGetDevisDuClient = undefined
                
                recupererClient(idClient);
                recupererEvenementsDuClient(idClient);
                recupererDevisDuClient(idClient);
                
                setFormulaireDisabled(true)
                
                $scope.showInfosClient = false;
			}

            function redirigerPageRechercheClients() {
                $location.path("/Clients").search({empty : 'true'}); 
            }

            // ---------------------------------
            // Recuperation du client
            // ---------------------------------
            function recupererClient(idClient) {
                controller.erreurEditionClient = undefined;
				controller.requettesHttpEnCours++;
                 if (!idClient) {
                        controller.erreurEditionClient = {libelle : "Le client n'a pas d'id de renseigné!"};
                        controller.requettesHttpEnCours--;
                 } else {
                     console.log("Avant get client");
                    ClientsServices.getClient(idClient).then(function(response) {
                        console.log("response get :", response);
                        $scope.client = response.data;
                        controller.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get client", error);
                        controller.erreurEditionClient = {libelle : "Erreur lors de la recherche du client " + idClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        controller.requettesHttpEnCours--;
                    });
                 }
			}
            
            // ---------------------------------
            // Recuperation des evenements du client
            // ---------------------------------
            function recupererEvenementsDuClient(idClient) {
                controller.erreurGetEvenementsDuClient = undefined;
				controller.requettesHttpEnCours++;
                 if (!idClient) {
                        controller.erreurGetEvenementsDuClient = {libelle : "Le client n'a pas d'id de renseigné. Quittez la page!"};
                        controller.requettesHttpEnCours--;
                 } else {
                     console.log("Avant getEvenementsDuClient");
                    EvenementsServices.getEvenementsDuClient(idClient).then(function(response) {
                        console.log("response get :", response);
                        $scope.evenements = response.data;
                        console.log("$scope.evenements :", $scope.evenements);
                        controller.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur getEvenementsDuClient ", error);
                         controller.erreurGetEvenementsDuClient = {libelle : "Erreur lors de la récupération des événements du client " + idClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        controller.requettesHttpEnCours--;
                    });
                 }
			}
            
            // ---------------------------------
            // Recuperation des devis du client
            // ---------------------------------
            function recupererDevisDuClient(idClient) {
                controller.erreurGetDevisDuClient = undefined;
				controller.requettesHttpEnCours++;
                 if (!idClient) {
                        controller.erreurGetDevisDuClient = {libelle : "Le client n'a pas d'id de renseigné. Quittez la page!"};
                        controller.requettesHttpEnCours--;
                 } else {
                     console.log("Avant getDevisByClientId");
                    DevisServices.getDevisByClientId(idClient).then(function(response) {
                        console.log("response get :", response);
                        $scope.devis = response.data;
                        console.log("$scope.devis :", $scope.devis);
                        controller.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur getDevisByClientId ", error);
                         controller.erreurGetDevisDuClient = {libelle : "Erreur lors de la récupération des devis du client " + idClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        controller.requettesHttpEnCours--;
                    });
                 }
			}
            
             function updateClient(client) {
                controller.erreurEditionClient = undefined;
				controller.requettesHttpEnCours++;
                 console.log("Avant post");
				ClientsServices.updateClient($scope.clientToUpdate).then(function(response) {
                    console.log("response update :", response);
					controller.requettesHttpEnCours--;
                    // Afficher message de mise à jour OK
                    $scope.updateOk=true;
				}, function (error) {
                    console.error("Erreur update ", error);
                    controller.erreurEditionClient = {libelle : "Erreur lors de l'édition du client " + $scope.clientToUpdate.numeroClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					controller.requettesHttpEnCours--;
                    $scope.updateOk=false;
				});
			}
            
            $scope.clickModification = function(path) {
                console.log("clickModification");
                $scope.submitted = false;
                $scope.infosClientEnCoursDeModification = true;
                $scope.clientToUpdate = angular.copy($scope.client);
                $scope.updateOk = false;
                setFormulaireDisabled(false);
            };
            
            $scope.annulerModification = function(path) {
                $scope.submitted = false;
                $scope.infosClientEnCoursDeModification = false;
                console.log("annulerModification");
                $scope.client = angular.copy($scope.clientToUpdate);
                $scope.updateOk = false;
                setFormulaireDisabled(true);
            };
            
            $scope.validerModification= function(path) {
                console.log("validerModification");
                if (!$scope.formulaireEditionClient.$valid) {
                    $scope.submitted = true;
                    $scope.infosClientEnCoursDeModification = true;
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : update du client");
                    // mettre a jour seulement si structures differentes?
                    $scope.clientToUpdate = angular.copy($scope.client);
                    updateClient($scope.client);
                    $scope.infosClientEnCoursDeModification = false;
                    $scope.submitted = true;
                }
                setFormulaireDisabled(!$scope.infosClientEnCoursDeModification);
            };
            
            function setFormulaireDisabled(disabled) {
                document.getElementById("fieldSetFormulaireEditionClient").disabled = disabled;
            }
            
            // Bouton creer evenement
            $scope.creerNouvelEvenement = function() {
                console.log("click creerNouvelEvenement");
                if ($scope.typeEvenement == 'CHAMBRE') {
                    $location.path("/NouvelleChambre").search({idClient: idClient}); 
                } else if ($scope.typeEvenement == 'REPAS') {
                    $location.path("/NouveauRepas").search({idClient: idClient});
                } else if ($scope.typeEvenement == 'LIBERTE') {
                    $location.path("/NouveauRepasLiberte").search({idClient: idClient});
                } else if ($scope.typeEvenement == 'LOCATION_SALLE') {
                    $location.path("/NouvelleLocation").search({idClient: idClient});
                } else if ($scope.typeEvenement == 'MARIAGE') {
                    $location.path("/NouveauMariage").search({idClient: idClient});
                }else {
                    alert("Not managed");
                }
            };
            
            // click evenement
            $scope.clickOnEvenement = function(evenement) {
                var idEvenement = evenement.id;
                console.log("evenement.typeEvenement=", evenement.typeEvenement);
                if (evenement.typeEvenement == 'MARIAGE') {
                    $location.path("/EditionMariage").search({idEvenement: idEvenement, idClient: idClient});
                } else if (evenement.typeEvenement == 'REPAS') {
                    $location.path("/EditionRepas").search({idEvenement: idEvenement, idClient: idClient});
                } else if (evenement.typeEvenement == 'LOCATION_SALLE') {
                    $location.path("/EditionLocation").search({idEvenement: idEvenement, idClient: idClient});
                }else if (evenement.typeEvenement == 'LIBERTE') {
                    $location.path("/EditionRepasLiberte").search({idEvenement: idEvenement, idClient: idClient});
                }else if (evenement.typeEvenement == 'CHAMBRE') {
                    $location.path("/EditionChambre").search({idEvenement: idEvenement, idClient: idClient});
                }else {
                    alert("Not managed");
                }                
            };
            
		}
	]);
})();
