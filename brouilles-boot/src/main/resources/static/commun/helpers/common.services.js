/**
 * Created by ijla5100 on 23/12/2016.
 */
(function() {

	"use strict";

	angular
			.module('ApplicationGestionnaireClients')
			.factory(
					'CommonServices',
					[
							'$http',

							function($http) {

								// Interface de la factory de services
								var services = {
									formatDateToDDMMYYYY : formatDateToDDMMYYYY,
                                    formatDateToSlashDDMMYYYY : formatDateToSlashDDMMYYYY,
                                    formatDateToYYYYMMDD : formatDateToYYYYMMDD,
                                    convertSlashDDMMYYYYToDateObject : convertSlashDDMMYYYYToDateObject,
                                    verifierDatePlusRecenteQue : verifierDatePlusRecenteQue
								};
                                
                                // convert date to dd-MM-yyyy
                                function formatDateToDDMMYYYY(date) {
                                    var d = new Date(date),
                                        month = '' + (d.getMonth() + 1),
                                        day = '' + d.getDate(),
                                        year = d.getFullYear();

                                    if (month.length < 2) month = '0' + month;
                                    if (day.length < 2) day = '0' + day;

                                    return [day, month, year].join('-');
                                }
                                
                                // convert date to dd-MM-yyyy
                                function formatDateToYYYYMMDD(date) {
                                    var d = new Date(date),
                                        month = '' + (d.getMonth() + 1),
                                        day = '' + d.getDate(),
                                        year = d.getFullYear();

                                    if (month.length < 2) month = '0' + month;
                                    if (day.length < 2) day = '0' + day;

                                    return [year, month, day].join('-');
                                }
                                
                                // convert date to dd/MM/yyyy
                                function formatDateToSlashDDMMYYYY(date) {
                                    var d = new Date(date),
                                        month = '' + (d.getMonth() + 1),
                                        day = '' + d.getDate(),
                                        year = d.getFullYear();

                                    if (month.length < 2) month = '0' + month;
                                    if (day.length < 2) day = '0' + day;

                                    return [day, month, year].join('/');
                                }
                                
                                // convert dd/MM/yyyy to date 
                                function convertSlashDDMMYYYYToDateObject(dateSlash) {
                                    var split = dateSlash.split("/");
                                    var day = split[0];
                                    var month = split[1];
                                    var year = split[2];
                                    // convert to yyyy-MM-dd
                                    var dateFormat=[year, month, day].join('-');
                                    return new Date(dateFormat);
                                }
                                
                                // Verifier que dateA <= dateB, en prenant en compte que la date mais pas l'heure
                                function verifierDatePlusRecenteQue(dateA, dateB) {
                                    console.error("test error");
                                    
                                    var valides = false;
                                    
                                    try {
                                       if (dateA && dateB) {
                                            console.log("Les 2 dates A et B sont definies");
                                           console.log("dateA:", dateA.toLocaleDateString());
                                           console.log("dateB:", dateB.toLocaleDateString());
                                           
                                           dateA.setHours(0,0,0,0);
                                           dateB.setHours(0,0,0,0);
                                           
                                           console.log("dateA:", dateA.toLocaleDateString());
                                           console.log("dateB:", dateB.toLocaleDateString());
                                           
                                           if (dateA > dateB) {
                                               console.error("La première date doit être anterieure à la seconde");
                                               valides = false;
                                           } else {
                                               valides = true;
                                           }
                                        }
                                    }
                                    catch(error) {
                                        console.error(error);
                                        valides = false;
                                    }
                                    return valides;
                                }
                                

								return services;
							} ]);
})();