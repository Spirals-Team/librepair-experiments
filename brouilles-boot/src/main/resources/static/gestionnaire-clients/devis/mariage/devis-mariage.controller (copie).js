/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('DevisMariageController', ['$scope', '$routeParams', '$location', '$window', 'DevisServices', 'ClientsServices', 'MariageServices', 

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
                recupererClient(idClient);
                recupererEvenement(idEvenement);
                $scope.proprietesDevisBasic = [];
                $scope.proprietesDevisAlimentaire= [];
                initialiserValeursDevis();
            }
            
            function initialiserValeursDevis() {
                var json = $scope.devis.json;
                json.proprietesMariage={};
                    
                // BASIC
                json.proprietesMariage.remiseTraiteurPlus70P = values(0,400 * -1);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.remiseTraiteurPlus70P);
                json.proprietesMariage.houssesChaises = values(0,2);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.houssesChaises);
                json.proprietesMariage.noeudsChaiseLycra = values(0,0.70);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.noeudsChaiseLycra);
                json.proprietesMariage.serveursAperitifs = values(0,52);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.serveursAperitifs);
                json.proprietesMariage.vaisselleAperitifNapageServiettes = values(0,1.30);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.vaisselleAperitifNapageServiettes);
                json.proprietesMariage.chapiteauSupplementaire = values(0,150);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.chapiteauSupplementaire);
                json.proprietesMariage.remiseExceptionnelle50Pc = values(0,75 * -1);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.remiseExceptionnelle50Pc);
                json.proprietesMariage.fontaineChampagne = values(0,120);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.fontaineChampagne);
                json.proprietesMariage.verresLinealx2 = values(0,0.54);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.verresLinealx2);
                json.proprietesMariage.gammeCerax3 = values(0,0.81);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.gammeCerax3);
                json.proprietesMariage.couvertsPixel = values(0,0.75);
                $scope.proprietesDevisBasic.push(json.proprietesMariage.couvertsPixel);
                json.proprietesMariage.sousTotalBasic = values(1,0);
                
                // On ajoute pas les sous totaux dans le tableau de proproete sinon ca fausse le calcul de somme!
                //$scope.proprietesDevisBasic.push(json.proprietesMariage.sousTotalBasic);
                    
                // ALIMENTAIRES
                json.proprietesMariage.menuRaffine = values(0,45);
                $scope.proprietesDevisAlimentaire.push(json.proprietesMariage.menuRaffine);
                json.proprietesMariage.menuEnfant = values(0,12);
                $scope.proprietesDevisAlimentaire.push(json.proprietesMariage.menuEnfant);
                json.proprietesMariage.painDeCampagne = values(0,55);
                $scope.proprietesDevisAlimentaire.push(json.proprietesMariage.painDeCampagne);
                json.proprietesMariage.painNordique = values(0,62);
                $scope.proprietesDevisAlimentaire.push(json.proprietesMariage.painNordique);
                json.proprietesMariage.plateau60Canapes = values(0,70);
                $scope.proprietesDevisAlimentaire.push(json.proprietesMariage.plateau60Canapes);
                json.proprietesMariage.brunch = values(0,15);
                $scope.proprietesDevisAlimentaire.push(json.proprietesMariage.brunch);
                json.proprietesMariage.sousTotalAlimentaire = values(1,0);
                
                // TOTAUX
                json.proprietesMariage.totalDevis = values(1,0);
                json.proprietesMariage.acompte = values(1,0);
                json.proprietesMariage.totalRestant = values(1,0);
                json.proprietesMariage.acompteAVerser = values(1,0);
                json.proprietesMariage.soldeARegler = values(1,0);
			}
            
            function defaultValues() {
                return values(0, 0.00);
            }
            
            function values(vQuantite, vPrixUnitaireTTC) {
                var values = {
                        quantite : vQuantite,
                        prixUnitaireTTC : vPrixUnitaireTTC,
                        prixUnitaireHT : round2Decimals(vPrixUnitaireTTC / 1.2),
                        prixTotalTTC : round2Decimals(vQuantite * vPrixUnitaireTTC),
                        prixTotalHT : round2Decimals(vQuantite * vPrixUnitaireTTC / 1.2)
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
                
                // BASIC
                var totalBasicTTC = 0;
                angular.forEach($scope.proprietesDevisBasic, function(proprieteBasic, index) {
                    totalBasicTTC = totalBasicTTC + proprieteBasic.prixTotalTTC;
                });
                $scope.devis.json.proprietesMariage.sousTotalBasic.prixTotalTTC = round2Decimals(totalBasicTTC);
                $scope.devis.json.proprietesMariage.sousTotalBasic.prixTotalHT = round2Decimals(totalBasicTTC / 1.2);
                
                // ALIMENTAIRE
                var totalAlimentaireTTC = 0;
                angular.forEach($scope.proprietesDevisAlimentaire, function(proprieteAlimentaire, index) {
                    totalAlimentaireTTC = totalAlimentaireTTC + proprieteAlimentaire.prixTotalTTC;
                });
                $scope.devis.json.proprietesMariage.sousTotalAlimentaire.prixTotalTTC = round2Decimals(totalAlimentaireTTC);
                $scope.devis.json.proprietesMariage.sousTotalAlimentaire.prixTotalHT = round2Decimals(totalAlimentaireTTC / 1.2);
                
                // Totaux
                $scope.devis.json.proprietesMariage.totalDevis.prixTotalTTC = round2Decimals(totalBasicTTC + totalAlimentaireTTC);
                $scope.devis.json.proprietesMariage.totalDevis.prixTotalHT = round2Decimals( (totalBasicTTC + totalAlimentaireTTC) / 1.2);
                
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
            
		}
	]);
})();
