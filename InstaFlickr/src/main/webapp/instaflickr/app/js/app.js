'use strict';

var instaflickr = angular.module('InstaFlickr', [
    'ngRoute',
    'InstaFlickControllers'
]);

instaflickr.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                when('/profile', {
                    templateUrl: 'partials/profile.html',
                    controller: 'ProfileCtrl'
                }).
                when('/picture', {
                    templateUrl: 'partials/picture.html',
                    controller: 'PictureCtrl'
                }).
                when('/settings', {
                    templateUrl: 'partials/settings.html'
                }).
                otherwise({
                    templateUrl: 'partials/feed.html'
                });
    }
]);