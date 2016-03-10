'use strict';

var instaflickr = angular.module('InstaFlickr', [
    'ui.router',
    'ngFileUpload',
    'InstaFlickControllers',
    'UserRegistryService',
    'MediaService'
]);

instaflickr.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: 'partials/login.html',
                controller: 'LoginCtrl'
            })
            .state('register', {
                url: '/register',
                templateUrl: 'partials/register.html',
                controller: 'RegisterCtrl'
            })
            .state('setupProfile', {
                url: '/setupProfile',
                templateUrl: 'partials/setupProfile.html',
                controller: 'SetupProfileCtrl'
            })
            .state('userProfile', {
                url: '/profile',
                templateUrl: 'partials/profile.html',
                controller: 'ProfileCtrl'
            })
            .state('profile', {
                url: '/profile/:username',
                templateUrl: 'partials/profile.html',
                controller: 'ProfileCtrl'
            })
            .state('picture', {
                url: '/picture/:id',
                templateUrl: 'partials/picture.html',
                controller: 'PictureCtrl'
            })
            .state('settings', {
                url: '/settings',
                templateUrl: 'partials/settings.html'
            })
            .state('about', {
                url: '/about',
                templateUrl: 'partials/about.html'
            })
            .state('upload', {
                url: '/upload',
                templateUrl: 'partials/upload.html',
                controller: 'UploadCtrl'
            })
            .state('album', {
                url: '/album/:username/:albumname',
                templateUrl: 'partials/album.html',
                controller: 'AlbumCtrl'
            })
            .state('home', {
                url: '/login',
                templateUrl: 'partials/login.html'
            });
});