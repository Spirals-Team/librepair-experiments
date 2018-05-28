'use strict';

var app = angular.module('myApp.view2', ['ngRoute', 'ui-leaflet'])

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view2', {
            templateUrl: 'app/view2/view2.html',
            controller: 'View2Ctrl'
        });
    }])
app.service('markerCities', function () {
    var property = new Array();

    return {
        getProperty: function () {
            return property;
        },
        setProperty: function (value) {
            property = value;
        },
        addProperty: function (value) {
            property.push(value);
        }

    };
});

app.controller('View2Ctrl', function ($scope, $http, markerCities) {
    $scope.searchByBookTitle = function () {
        $http({
            method: 'GET',
            url: 'api/cities/findByBookTitle',
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
            console.log("ERROR FOUND s::> " + JSON.stringify(response));
            $scope.cities = null;
            $scope.err = response.data.error;
        });
    };


});