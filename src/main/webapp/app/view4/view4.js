'use strict';

var app = angular.module('myApp.view4', ['ngRoute', 'ui-leaflet'])

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view4', {
            templateUrl: 'app/view4/view4.html',
            controller: 'View4Ctrl'
        });
    }])

app.service('booksGlobal', function () {
    var books = new Array();

    return {
        getBooks: function () {
            return books;
        },
        setBooks: function (value) {
            books = value;
        }
    };
});

app.controller('View4Ctrl', function ($scope, $http, booksGlobal) {
    $scope.searchBooksByGeolocation = function () {
        $http({
            method: 'GET',
            url: 'api/books/findByGeolocation',
            params: {lat: $scope.toSearch, lon:$scope.toSearch2, db: $scope.db}
        }).then(function successCallback(response) {
            $scope.books = response.data;
            $scope.markers = {};
            angular.forEach($scope.books, function (book) {
                angular.forEach(book.cities, function (city) {
                    if (city.name in $scope.markers) {
                        $scope.markers[city.name].message = $scope.markers[city.name].message + "<dd>" + book.bookTitle + "</dd>";
                    } else {
                        $scope.markers[city.name] = {
                            lat: city.lat,
                            lng: city.lon,
                            message: "<dt>" + city.name + "</dt><dd>" + book.bookTitle + "</dd>",
                            focus: false,
                            draggable: false
                        };
                    }
                });

            });

            angular.forEach($scope.markers, function (marker) {
                marker.message = "<dl>" + marker.message + "</dl>";
            });

            $scope.err = null;
        }, function errorCallback(response) {
            console.log("ERROR FOUND::> " + JSON.stringify(response));
            $scope.cities = null;
            $scope.books = null;
            $scope.err = response.data.error;
        });
    };

});
