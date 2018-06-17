/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('EditionDevisMariageController', ['$scope', '$routeParams', '$location', '$window', 'DevisServices', 'ClientsServices', 'MariageServices', 'DocumentsServices', 

		function ($scope,$routeParams, $location, $window, DevisServices, ClientsServices, MariageServices, DocumentsServices) {

			var controller = this;     
            var idClient = $routeParams.idClient;
            var idEvenement = $routeParams.idEvenement;
            var idDevis= $routeParams.idDevis;
            
			initialiser();

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                $scope.infosEvenementEnCoursDeModification = false;
                $scope.erreurDevisMariage = undefined;
                $scope.proprietesDevisBase = [];
                $scope.proprietesDevisAlimentaire = [];
                recupererClient(idClient);
                recupererEvenement(idEvenement);
                recupererDevis(idDevis);
                setFormulaireDisabled(true);
            }

            function round2Decimals(value) {
              return round(value,2);
            }
            
            function round(value, decimals) {
              return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
            }
            
            $scope.changeQuantite = function(propriete) {
                modiferApresChangementQuantite(propriete);
            }
            
            $scope.changePrixUnitaireTTC = function(propriete) {
                modiferApresChangementPrixUnitaireTTC(propriete)
            }
            
            function modiferApresChangementQuantite(propriete){
                if (propriete.quantite) {
                    updatePrixTotal(propriete);
                } else {
                    resetPrixTotal(propriete);
                }
                updateTotaux();
            }
            
            function modiferApresChangementPrixUnitaireTTC(propriete){
                if (propriete.prixUnitaireTTC) {
                    updatePrixTotal(propriete);
                } else {
                    resetPrixTotal(propriete);
                }
                updateTotaux();
            }
            
            function updatePrixTotal(propriete) {
                propriete.prixTotalTTC = round2Decimals(propriete.quantite * propriete.prixUnitaireTTC);
                propriete.prixTotalHT = round2Decimals(propriete.prixTotalTTC / 1.2);
            }
            
            function resetPrixTotal(propriete) {
                propriete.prixTotalTTC = round2Decimals(0);
                propriete.prixTotalHT = round2Decimals(0);
            }
            
            function updateTotaux() {
                
                // BASE
                var totalBaseTTC = 0;
                angular.forEach($scope.proprietesDevisBase, function(proprieteBase, index) {
                    totalBaseTTC = totalBaseTTC + proprieteBase.prixTotalTTC;
                });
                $scope.devis.json.proprietesMariage.sousTotalBase.prixTotalTTC = round2Decimals(totalBaseTTC);
                $scope.devis.json.proprietesMariage.sousTotalBase.prixTotalHT = round2Decimals(totalBaseTTC / 1.2);
                
                // ALIMENTAIRE
                var totalAlimentaireTTC = 0;
                angular.forEach($scope.proprietesDevisAlimentaire, function(proprieteAlimentaire, index) {
                    totalAlimentaireTTC = totalAlimentaireTTC + proprieteAlimentaire.prixTotalTTC;
                });
                $scope.devis.json.proprietesMariage.sousTotalAlimentaire.prixTotalTTC = round2Decimals(totalAlimentaireTTC);
                $scope.devis.json.proprietesMariage.sousTotalAlimentaire.prixTotalHT = round2Decimals(totalAlimentaireTTC / 1.2);
                
                // Totaux
                $scope.devis.json.proprietesMariage.totalDevis.prixTotalTTC = round2Decimals(totalBaseTTC + totalAlimentaireTTC);
                $scope.devis.json.proprietesMariage.totalDevis.prixTotalHT = round2Decimals( (totalBaseTTC + totalAlimentaireTTC) / 1.2);
                
                //$scope.devis.json.proprietesMariage.totalDevis.proprietesMariage.acompte = values(1,0);
                var totalDevisTTC = $scope.devis.json.proprietesMariage.totalDevis.prixTotalTTC;
                var acompteTTC = $scope.devis.json.proprietesMariage.acompte.prixTotalTTC;
                console.log("totalDevisTTC:", totalDevisTTC);
                $scope.devis.json.proprietesMariage.totalRestant.prixTotalTTC = round2Decimals(totalDevisTTC - acompteTTC);
                $scope.devis.json.proprietesMariage.totalRestant.prixTotalHT = round2Decimals((totalDevisTTC - acompteTTC) / 1.2);
                
                var totalRestantTTC = $scope.devis.json.proprietesMariage.totalRestant.prixTotalTTC;
                $scope.devis.json.proprietesMariage.acompteAVerser.prixTotalTTC = round2Decimals(totalRestantTTC * 0.3);
                $scope.devis.json.proprietesMariage.acompteAVerser.prixTotalHT = round2Decimals((totalRestantTTC * 0.3) / 1.2);
                
                var acompteAVerserTTC = $scope.devis.json.proprietesMariage.acompteAVerser.prixTotalTTC;
                
                $scope.devis.json.proprietesMariage.soldeARegler.prixTotalTTC  = round2Decimals(totalRestantTTC - acompteAVerserTTC);
                $scope.devis.json.proprietesMariage.soldeARegler.prixTotalHT  = round2Decimals((totalRestantTTC - acompteAVerserTTC ) / 1.2);
            }
            
             function updateDevis(devis) {
                $scope.erreurDevisMariage = undefined;
				$scope.requettesHttpEnCours++;
                 console.log("Avant put");
                 
                 // On doit faire une copie car on stringify le json!
                 var devisPost = angular.copy(devis);
                 devisPost.json = JSON.stringify(devisPost.json);     
				DevisServices.updateDevis(devisPost).then(function(response) {
                    console.log("response update :", response);
					$scope.requettesHttpEnCours--;
                    // Afficher message de mise a jour OK
                    $scope.updateOk=true;
				}, function (error) {
                    console.error("Erreur update ", error);
                    $scope.erreurDevisMariage = {libelle : "Erreur lors de la mise à jour du devis" + devisPost.numeroDevis, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					$scope.requettesHttpEnCours--;
                    $scope.updateOk=false;
				});
			}
            
            function recupererClient(idClient) {
                $scope.erreurDevisMariage = undefined;
				$scope.requettesHttpEnCours++;
                 if (!idClient) {
                        $scope.erreurDevisMariage = {libelle : "Le client n'a pas d'id de renseigné!"};
                        $scope.requettesHttpEnCours--;
                 } else {
                     console.log("Avant get client");
                    ClientsServices.getClient(idClient).then(function(response) {
                        console.log("response get :", response);
                        $scope.client = response.data;
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get client", error);
                        $scope.erreurDevisMariage = {libelle : "Erreur lors de la recherche du client " + idClient, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                    });
                 }
			}
            
            // ---------------------------------
            // Recuperation du client
            // ---------------------------------
            function recupererEvenement(idEvenement) {
                console.log("recup evenement");
                $scope.erreurDevisMariage = undefined;
				$scope.requettesHttpEnCours++;
                 if (!idEvenement) {
                        $scope.erreurDevisMariage = {libelle : "L'événement n'a pas d'id de renseigné!"};
                        $scope.requettesHttpEnCours--;
                 } else {
                     console.log("Avant get mariage");
                    MariageServices.getEvenementById(idEvenement).then(function(response) {
                        console.log("response get :", response);
                        $scope.evenement = response.data;
                        console.log("mariage=", $scope.evenement);                        
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get mariage", error);
                        $scope.erreurDevisMariage = {libelle : "Erreur lors de la recherche de l'événement " + idEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                        setFormulaireDisabled(true);
                    });
                 }
			}
            
            // ---------------------------------
            // Recuperation du devis
            // ---------------------------------
            function recupererDevis(idDevis) {
                console.log("recup devis");
                $scope.erreurDevisMariage = undefined;
				$scope.requettesHttpEnCours++;
                 if (!idDevis) {
                        $scope.erreurDevisMariage = {libelle : "Le devis n'a pas d'id de renseigné!"};
                        $scope.requettesHttpEnCours--;
                 } else {
                     console.log("Avant get devis");
                    DevisServices.getDevisById(idDevis).then(function(response) {
                        console.log("response get :", response);
                        $scope.devis = response.data;
                        console.log("devis=", $scope.devis); 
                        
                        $scope.devis.json = JSON.parse($scope.devis.json);
                        
                        angular.forEach($scope.devis.json.proprietesMariage, function(propriete, index) {
                            
                            if (propriete.type == 'BASE') {
                                $scope.proprietesDevisBase.push(propriete);
                            } else if (propriete.type == 'ALIMENTAIRE') {
                                $scope.proprietesDevisAlimentaire.push(propriete);
                            }
                            
                        });
                        
                        $scope.requettesHttpEnCours--;
                    }, function (error) {
                        console.error("Erreur get devis", error);
                        $scope.erreurDevisMariage = {libelle : "Erreur lors de la recherche du devis" + idDevis, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                        $scope.requettesHttpEnCours--;
                        setFormulaireDisabled(true);
                    });
                 }
			}
            
            function setFormulaireDisabled(disabled) {
                $scope.formDisabled=disabled;
            }
            
             function postDevis(devis) {
                $scope.erreurDevisMariage = undefined;
				$scope.requettesHttpEnCours++;
                 
                var devisPost = angular.copy(devis);                 

                devisPost.json = JSON.stringify(devisPost.json);     
                 
				DevisServices.postDevis(devisPost).then(function(response) {
                    console.log("response post :", response);
					$scope.requettesHttpEnCours--;
                    
                    // Appel OK : on redirige vers la page d'édition du mariage
                    redirigerPageEditionMariage();
				}, function (error) {
                    console.error("Erreur post ", error);
                    $scope.erreurDevisMariage = {libelle : "Erreur lors de la creation du devis ", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                    $scope.requettesHttpEnCours--;
				});
			}
            
            function redirigerPageEditionMariage() {
                // La création de l'évenement se fait à partir de cette page,
                // on redirige donc là.
                $location.path("/EditionMariage").search({idEvenement: idEvenement, idClient: idClient});
            }
            
            $scope.clickModification = function(path) {
                console.log("clickModification");
                $scope.submitted = false;
                $scope.infosEvenementEnCoursDeModification = true;
                $scope.devisToUpdate = angular.copy($scope.devis);
                $scope.updateOk = false;
                setFormulaireDisabled(false);
            };
            
            $scope.annulerModification = function(path) {
                $scope.submitted = false;
                $scope.infosEvenementEnCoursDeModification = false;
                console.log("annulerModification");
                $scope.devis = angular.copy($scope.devisToUpdate);
                $scope.updateOk = false;
                setFormulaireDisabled(true);
            };
            
            $scope.validerModification= function(path) {
                console.log("validerModification");
                if (!$scope.formulaireDevisMariage.$valid) {
                    $scope.submitted = true;
                    $scope.infosEvenementEnCoursDeModification = true;
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : update du devis");
                    // mettre a jour seulement si structures differentes?
                    $scope.devisToUpdate = angular.copy($scope.devis);
                    //formatFrontMariageToServer($scope.evenementToUpdate);
                    updateDevis($scope.devisToUpdate);
                    $scope.infosEvenementEnCoursDeModification = false;
                    $scope.submitted = true;
                }
                setFormulaireDisabled(!$scope.infosEvenementEnCoursDeModification);
            };
            
            $scope.isRemise = function(prop) {
                return prop.remise;
            };
            
            $scope.telechargerDevis = function() {
                DocumentsServices.downloadDevisInPdf($scope.devis);
            };
            
		}
	]);
})();
