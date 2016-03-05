'use strict';

var instaflickr = angular.module('InstaFlickr', [
    'ngRoute',
    'ngFileUpload',
    'InstaFlickControllers',
    'UserRegistryService',
    'MediaService'
]);

instaflickr.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                when('/login', {
                    templateUrl: 'partials/login.html',
                    controller: 'LoginCtrl'
                }).
                when('/register', {
                    templateUrl: 'partials/register.html',
                    controller: 'RegisterCtrl'
                }).                
                when('/setupProfile',{
                    templateUrl: 'partials/setupProfile.html',
                    controller: 'SetupProfileCtrl'
                }).                
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
                when('/about', {
                    templateUrl: 'partials/about.html'
                }).
                when('/upload', {
                    templateUrl: 'partials/upload.html',
                    controller: 'UploadCtrl'
                }).
                when('/', {
                    templateUrl: 'partials/login.html'
                }).
                otherwise({
                    templateUrl: 'partials/notfound.html'
                })
                ;
    }
]);