'use strict';

var app = angular.module('myApp.home', ['ngRoute'])

        app.config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/home', {
                    templateUrl: 'app/home/home.html',
                });
            }])
