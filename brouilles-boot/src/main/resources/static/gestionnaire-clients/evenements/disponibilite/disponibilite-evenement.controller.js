/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('DisponibiliteEvenementController', ['$scope', '$uibModal','CommonServices', 'EvenementsServices',

		function ($scope, $uibModal,CommonServices, EvenementsServices) {
 
			initialiser();
            
            
            ////////////////////////////////////////////////////////////////////:

			function initialiser() {
                console.log("Initialiser DisponibiliteEvenementController");
                console.log("$scope.evenement=", $scope.evenement);
                $scope.disponibilite={mariageLendemain : false};
				$scope.disponibilite.requettesHttpEnCours = 0 ;
                $scope.disponibilite.erreurGetEvenementsInRange=undefined;
                    
                $scope.disponibilite.evenements=[];
                convertirFormatDates();
                getEvenementsInRange();
                getEvenementsDuJourAvecTypesMariage();
			}
            
            // Récupération des evenements dans la plage
			function getEvenementsInRange() {
                $scope.disponibilite.erreurGetEvenementsInRange = undefined;
				$scope.disponibilite.requettesHttpEnCours++;
                console.log("Recherche des evenements compris entre ", $scope.disponibilite.dateDebut + " et " + $scope.disponibilite.dateFin);
                
				EvenementsServices.getEvenementsInRange($scope.disponibilite.dateDebut, $scope.disponibilite.dateFin).then(function(response) {
                    $scope.disponibilite.evenements = response.data;
                    console.log("getEvenementsInRange=", $scope.disponibilite.evenements);
					$scope.disponibilite.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getEvenementsInRange : ", error);
					$scope.disponibilite.erreurGetEvenementsInRange = {libelle : "Erreur lors de la récupération des évenements in range", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					$scope.disponibilite.requettesHttpEnCours--;
				});
			}
            
			function getEvenementsDuJourAvecTypesMariage() {
                $scope.disponibilite.erreurGetEvenementsInRange = undefined;
				$scope.disponibilite.requettesHttpEnCours++;
                
                var lendemain = getDateDuLendemain();
                console.log("Recherche des mariage le : ", lendemain);
                
				EvenementsServices.getEvenementsDuJourAvecTypes(lendemain, "MARIAGE").then(function(response) {
                    console.log("getEvenementsDuJourAvecTypes=", response.data);
                    $scope.disponibilite.mariageLendemain = (response.data.length > 0);
					$scope.disponibilite.requettesHttpEnCours--;
				}, function (error) {
                    console.error("error getEvenementsDuJourAvecTypes : ", error);
					$scope.disponibilite.erreurGetEvenementsInRange = {libelle : "Erreur lors de la récupération des évenements mariage du lendemain", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
					$scope.disponibilite.requettesHttpEnCours--;
                    $scope.disponibilite.mariageLendemain = false;
				});
			}
            
			function convertirFormatDates() {
                $scope.disponibilite.dateDebut = CommonServices.formatDateToDDMMYYYY($scope.evenement.debutTmp);
                if ($scope.evenement.finTmp) {
                    $scope.disponibilite.dateFin = CommonServices.formatDateToDDMMYYYY($scope.evenement.finTmp);
                } else {
                    $scope.disponibilite.dateFin = angular.copy($scope.disponibilite.dateDebut);
                }
                
                $scope.disponibilite.dateDebutAffichage = CommonServices.formatDateToSlashDDMMYYYY($scope.evenement.debutTmp);
                if ($scope.evenement.finTmp) {
                    $scope.disponibilite.dateFinAffichage = CommonServices.formatDateToSlashDDMMYYYY($scope.evenement.finTmp);
                } else {
                    $scope.disponibilite.dateFinAffichage = angular.copy($scope.disponibilite.dateDebutAffichage);
                }
			}
            
            function getDateDuLendemain() {
                var lendemain = new Date($scope.evenement.finTmp);
                console.log("lendemain avant conversion=", lendemain);
                lendemain.setDate(lendemain.getDate() + 1);
                console.log("lendemain apres conversion=", lendemain);
                return CommonServices.formatDateToDDMMYYYY(lendemain);
            }
		}
        
	]);
})();
