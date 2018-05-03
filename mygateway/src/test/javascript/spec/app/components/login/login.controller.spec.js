'use strict';

describe('Controller Tests', function () {

    beforeEach(module('mygatewayApp'));

    describe('LoginController', function () {
        var $scope, authService;

        beforeEach(inject(function ($rootScope, $controller, Auth) {
            $scope = $rootScope.$new();
            authService = Auth;
            $controller('LoginController as vm',
                {
                    $scope: $scope,
                    Auth: authService,
                    $uibModalInstance: null
                });
        }));

        it('should set remember Me', function () {
            expect($scope.vm.rememberMe).toBeTruthy();
        });
    });
});
