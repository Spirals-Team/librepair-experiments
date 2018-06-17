/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('NouveauDevisMariageController', ['$scope', '$routeParams', '$location', '$window', 'DevisServices', 'ClientsServices', 'MariageServices', 

		function ($scope,$routeParams, $location, $window, DevisServices, ClientsServices, MariageServices) {

			var controller = this;     
            var idClient = $routeParams.idClient;
            var idEvenement = $routeParams.idEvenement;
            
            $scope.devis={
                typeDevis : "MARIAGE",
                evenementId : idEvenement,
                etat : 'CREE',
                json : {}
            };
            
			initialiser();

			function initialiser() {
				$scope.requettesHttpEnCours = 0;
                $scope.erreurDevisMariage = undefined;
                $scope.proprietesDevisBase = [];
                $scope.proprietesDevisAlimentaire = [];
                recupererClient(idClient);
                recupererEvenement(idEvenement);
                recupererProprietesDevisMariage();
            }
            
            function initialiserValeursDevis() {
                var json = $scope.devis.json;
                    
                // BASE
                json.proprietesMariage.sousTotalBase = values(1,0);
                    
                // ALIMENTAIRES
                json.proprietesMariage.sousTotalAlimentaire = values(1,0);
                
                // TOTAUX
                json.proprietesMariage.totalDevis = values(1,0, "Total");
                
                json.proprietesMariage.acompte = values(1,0, "Acompte versé pour la salle");
                json.proprietesMariage.totalRestant = values(1,0, "Total restant");
                json.proprietesMariage.acompteAVerser = values(1,0, "Acompte à verser");
                json.proprietesMariage.soldeARegler = values(1,0, "Solde à régler");
			}
            
            function defaultValues() {
                return values(0, 0.00);
            }
            
            function values(vQuantite, vPrixUnitaireTTC, vLabel = "") {
                var values = {
                        quantite : vQuantite,
                        prixUnitaireTTC : vPrixUnitaireTTC,
                        prixUnitaireHT : round2Decimals(vPrixUnitaireTTC / 1.2),
                        prixTotalTTC : round2Decimals(vQuantite * vPrixUnitaireTTC),
                        prixTotalHT : round2Decimals(vQuantite * vPrixUnitaireTTC / 1.2),
                        label : vLabel
                    };
                return values;
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
            
            function recupererProprietesDevisMariage() {
                console.log("recup evenement");
                $scope.erreurDevisMariage = undefined;
				$scope.requettesHttpEnCours++;
                console.log("Avant recupererProprietesDevisMariage");
                DevisServices.getProprietesDevisMariage().then(function(response) {
                    console.log("response get :", response);
                    $scope.devis.json.proprietesMariage = response.data;
                    
                    // Store proprietes par type
                    angular.forEach($scope.devis.json.proprietesMariage, function(propriete, index) {
                        
                        if (propriete.type == 'BASE') {
                            $scope.proprietesDevisBase.push(propriete);
                        } else if (propriete.type == 'ALIMENTAIRE') {
                            $scope.proprietesDevisAlimentaire.push(propriete);
                        } else {
                            throw "Le type de propriete " + propriete.type + " n'est pas géré!" ;
                        }
                        
                    });
                    
                    initialiserValeursDevis();
                    
                    console.log("$scope.devis.json :", $scope.devis.json);
                    $scope.requettesHttpEnCours--;
                }, function (error) {
                    console.error("Erreur recupererProprietesDevisMariage", error);
                    $scope.erreurDevisMariage = {libelle : "Erreur lors de la recherche de l'événement " + idEvenement, detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                    $scope.requettesHttpEnCours--;
                    setFormulaireDisabled(true);
                });
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
            
            $scope.annulerDevis = function() {
                console.log("annulerDevis");
                redirigerPageEditionMariage();
            };
            
            $scope.validerDevis = function() {
                console.log("validerDevis");
                $scope.submitted = true;
                console.log("$scope.formulaireDevisMariage", $scope.formulaireDevisMariage);
                if (!$scope.formulaireDevisMariage.$valid) {
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : creation du devis");
                    postDevis($scope.devis);
                }
            };
            
            $scope.isRemise = function(prop) {
                return prop.remise;
            };
            
            
		}
	]);
})();
