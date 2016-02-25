'use strict';

var instaflickr = angular.module('InstaFlickr', [
    'ngRoute'
]);

instaflickr.config(['$routeProvider',
    function routeProvider($routeProvider) {
        $routeProvider().when('profile', {
            templateUrl: 'partials/profile.html',
            controller: 'ProfileCtrl'
        });
    }
]);