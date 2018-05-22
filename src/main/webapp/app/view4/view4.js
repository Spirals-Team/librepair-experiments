'use strict';

var app = angular.module('myApp.view4', ['ngRoute'])

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view4', {
            templateUrl: 'app/view4/view4.html'
        });
    }])

app.controller('View2Ctrl', function ($scope, $http, markerCities) {
    $scope.searchByBookTitle = function () {
        $http({
            method: 'GET',
            url: 'api/city',
            params: {title: $scope.toSearch, db: $scope.db}
        }).then(function successCallback(response) {
            $scope.cities = response.data;
            $scope.err = null;
            $scope.markers = new Array();
            angular.forEach($scope.cities, function (product) {
                $scope.markers.push({
                    lat: product.lat,
                    lng: product.lon,
                    message: product.name,
                    focus: false,
                    draggable: false
                });
            });
        }, function errorCallback(response) {
            console.log("ERROR FOUND::> " + response.data);
            $scope.err = response.error;
            $scope.cities = null;
        });
    };


});