'use strict';

var jqmControllers = angular.module('jqmControllers');

jqmControllers.controller('µUserListCtrl', function($scope, $http, µUserDto, µRoleDto)
{
    $scope.users = null;
    $scope.roles = null;
    $scope.selected = [];
    $scope.minDate = new Date();
    $scope.opened = false;

    $scope.newitem = function()
    {
        var t = new µUserDto({
            login : 'login',
            locked : false,
            expirationDate : (new Date()).addDays(3650),
            creationDate : new Date(),
            freeText : 'user name or service name',
        });
        $scope.users.push(t);
        $scope.usr = t;
    };

    $scope.saveitem = function()
    {
        $scope.usr.$save($scope.refresh);
    };

    $scope.deleteitem = function()
    {
        if ($scope.usr == null)
        {
            return;
        }
        if ($scope.usr.id != undefined)
        {
            $scope.usr.$remove({
                id : $scope.usr.id
            });
        }
        $scope.users.splice($scope.users.indexOf($scope.usr), 1);
        $scope.usr = $scope.users[0];
    };

    $scope.save = function()
    {
        // Save and refresh the table - ID may have been generated by the server.
        µUserDto.saveAll({}, $scope.prms, $scope.refresh);
    };

    $scope.refreshdone = function()
    {
        if ($scope.users.length > 0)
        {
            $scope.usr = $scope.users[0];
        }
        else
        {
            $scope.usr = undefined;
        }
    };

    $scope.refresh = function()
    {
        $scope.selected.length = 0;
        $scope.users = µUserDto.query($scope.refreshdone);
        $scope.roles = µRoleDto.query();
    };

    $scope.dateOptions = {
        formatYear : 'yy',
        startingDay : 1
    };

    $scope.open = function($event)
    {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.refresh();
});
