/**
 * Created by ijla5100 on 23/12/2016.
 */
(function () {
	"use strict";

	angular.module("ApplicationGestionnaireClients", ["ngRoute",
																					"xeditable",
																				   "selectionModel",
                                                                                    "ui.bootstrap",
                                                                                    "angularUtils.directives.dirPagination",
                                                                                    "mwl.calendar",
                                                                                    "oc.lazyLoad"
	]);

	angular.module('ApplicationGestionnaireClients').run(function(editableOptions, editableThemes, $window) {
		//configuration bootstrap
		editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be 'bs3', 'bs2', 'default'
		editableThemes.bs3.inputClass = 'input-sm';
		editableThemes.bs3.buttonsClass = 'btn-sm';
	});
})();
