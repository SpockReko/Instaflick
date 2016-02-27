'use strict';

var instaFlickControllers = angular.module('InstaFlickControllers', []);

// Profile controller
instaFlickControllers.controller('ProfileCtrl', ['$scope',
    function ($scope) {

        $scope.name = "Henry Ottervad";
        $scope.description = "I like long walks on the beach..."

        var imageData = ['http://placehold.it/400x400', 'http://placehold.it/400x400',
            'http://placehold.it/400x400', 'http://placehold.it/400x400', 'http://placehold.it/400x400',
            'http://placehold.it/400x400', 'http://placehold.it/400x400', 'http://placehold.it/400x400'];
        
        $scope.images = imageData;

    }]);