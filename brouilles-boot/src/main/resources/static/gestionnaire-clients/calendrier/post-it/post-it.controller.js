/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module('ApplicationGestionnaireClients').controller('PostITController', ['$scope', '$location', 'PostITServices', 'CommonServices',

		function ($scope, $location, PostITServices, CommonServices) {
                                        
			initialiser();

            // Date d'aujourdhui au format yyyy-mm-dd
            var now = new Date();
            var today = CommonServices.formatDateToYYYYMMDD(now);
            
            $scope.postit.evenement.debutTmp=$scope.currentCalendarDate;
            
            ////////////////////////////////////////////////////////////////////:

			function initialiser() {
				$scope.postit.evenement={}
				$scope.postit.requettesHttpEnCours = 0 ;
                $scope.postit.erreurPostEvenement=undefined;
                $scope.postit.submitted = false;
			}
            
             function postEvenement(postit) {
                $scope.postit.erreurPostEvenement = undefined;
				$scope.postit.requettesHttpEnCours++;
                 
                console.log("Post de l'evenement:", postit);
                 
				PostITServices.postEvenement(postit).then(function(response) {
                    console.log("response post :", response);
					$scope.postit.requettesHttpEnCours--;
                    
                    // On ferme le modal car tout est ok
                    $scope.$close();
                                        
				}, function (error) {
                    console.error("Erreur post ", error);
                    $scope.postit.erreurPostEvenement = {libelle : "Erreur lors de la creation du post-it ", detail : (error.data ? "Code : " + error.data.status + ".\n Erreur : " + error.data.message : ""), detailChecked : false};
                    $scope.postit.requettesHttpEnCours--;
				});
			}
            
            $scope.creerPostIt = function(){
                console.log("creerPostIt");
                $scope.postit.submitted = true;
                if (!$scope.formulaireEvenement.$valid) {
                    console.log("Formulaire non valide");
                } else {
                    console.log("Formulaire valide : creation du post-it");
                    convertDate($scope.postit.evenement);
                    postEvenement($scope.postit.evenement);
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
