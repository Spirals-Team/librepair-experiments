'use strict';

var app = angular.module('myApp.view1', ['ngRoute'])

        app.config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        app.controller('View1Ctrl', function ($scope, $http) {
            $scope.toSearch = '';
            $scope.searchByCity = function () {
                $http({
                    method: 'GET',
                    url: 'api/book/city',
                    params: {city: $scope.toSearch, db: $scope.db}
                }).then(function successCallback(response) {
                    $scope.books = response.data;
                    $scope.err = null;
                }, function errorCallback(response) {
                    console.log("ERROR FOUND s::> " + JSON.stringify(response));
                    $scope.books = null;
                    $scope.err = response.data.error;
                });
            };

        });