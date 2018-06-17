/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('EditionPostITController', ['postit', '$scope', '$location', 'PostITServices', 'CommonServices',

		function (postit, $scope, $location, PostITServices, CommonServices) {
                       
            $scope.postit = postit;
            console.log("EditionPostITController $scope.postit = ", $scope.postit);
            
			initialiser();          
            
            ////////////////////////////////////////////////////////////////////:

			function initialiser() {
				$scope.postit.requettesHttpEnCours = 0 ;
                $scope.postit.erreurPostEvenement=undefined;
                $scope.postit.submitted = false;
                
                $scope.postit.evenement.debutTmp = CommonServices.convertSlashDDMMYYYYToDateObject($scope.postit.evenement.debut);
                $scope.postit.evenement.heureDebutTmp = angular.copy($scope.postit.evenement.debutTmp);
                
                var heurMinuteArray = $scope.postit.evenement.heureDebut.split(":");
                $scope.postit.evenement.heureDebutTmp.setHours(heurMinuteArray[0], heurMinuteArray[1]);
                
			}
            
             function update(postit) {
                $scope.postit.erreurPostEvenement = undefined;
				$scope.postit.requettesHttpEnCours++;
                 
                console.log("update de l'evenement:", postit);
                 
				PostITServices.update(postit).then(function(response) {
                    console.log("response update :", response);
					$scope.postit.requettesHttpEnCours--;
                    
                    // On ferme le modal car tout est ok
                    $scope.$close();
                                        
				}, function (error) {
                    console.error("Erreur post ", error);
                    $scope.postit.erreurPostEvenement = {libelle : "Erreur lors de la creation du post-it ", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                    $scope.postit.requettesHttpEnCours--;
				});
			}
            
            $scope.modifierPostIt = function(){
                console.log("modifierPostIt");
                $scope.postit.submitted = true;
                if (!$scope.formulaireEvenement.$valid) {
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : creation du post-it");
                    convertDate($scope.postit.evenement);
                    update($scope.postit.evenement);
                }
            };
            
            // Dans un service commun!!!!
            function convertDate(evenement) {
                if (evenement.debutTmp) {
                    // Convert date
                    var date_new = new Date(evenement.debutTmp);
                    date_new= date_new.toLocaleDateString();
                    evenement.debut = date_new;
                    console.log("evenement.debut = ",evenement.debut);
                }
                if (evenement.heureDebutTmp) {
                    // Convert date
                    var date_new = new Date(evenement.heureDebutTmp);
                    var time_new= date_new.toLocaleTimeString();
                    evenement.heureDebut = time_new.substring(0,5);
                    console.log("evenement.heureDebut = ",evenement.heureDebut);
                }
            }
            
		}
	]);
})();
