(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .factory('authInterceptor', authInterceptor);

    authInterceptor.$inject = ['$rootScope', '$q', '$location', '$localStorage', '$sessionStorage'];

    function authInterceptor ($rootScope, $q, $location, $localStorage, $sessionStorage) {
        var service = {
            request: request
        };

        return service;

        function request (config) {
            if (!config || !config.url || /^http/.test(config.url)) return config;

            /*jshint camelcase: false */
            config.headers = config.headers || {};
            var token = $localStorage.authenticationToken || $sessionStorage.authenticationToken;
            return config;
        }
    }
})();
