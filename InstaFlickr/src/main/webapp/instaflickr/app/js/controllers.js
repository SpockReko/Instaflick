'use strict';

var instaFlickControllers = angular.module('InstaFlickControllers', []);

// Navigation controller
instaFlickControllers.controller('NavigationCtrl', ['$scope', '$location',
    function ($scope, $location) {
        $scope.navigate = function (url) {
            $location.path(url);
        };

    }]);