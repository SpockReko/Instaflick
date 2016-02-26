'use strict';

var instaflickr = angular.module('InstaFlickr', [
    'ngRoute'
]);

instaflickr.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                when('/profile', {
                    templateUrl: 'partials/profile.html'
                }).
                when('/settings', {
                    templateUrl: 'partials/settings.html'
                }).
                otherwise({
                    templateUrl: 'partials/feed.html'
                });
    }
]);