'use strict';

var instaFlickControllers = angular.module('InstaFlickControllers', []);

// Register user controller
instaFlickControllers.controller('RegisterCtrl', 
    ['$scope', '$location', 'UserRegistryProxy',
    function ($scope, $location, UserRegistryProxy) {
        
        $scope.goBack = function() {
            window.history.back();
        }
        
        $scope.save = function() {
            Console.log("Saving user in RegisterCtrl");
            UserRegistryProxy.create($scope.user.username, $scope.user.password)
                    .success(function() {
                        Console.log("location: " + $location);
                        $location.path('/login');
                    }).error(function() {
                        Console.log("Error in save RegisterCtrl: " + $location);
                         // TODO;
                    });
        };        
    }
]);
// Profile controller
instaFlickControllers.controller('ProfileCtrl', ['$scope',
    function ($scope) {

        $scope.name = "Henry Ottervad";
        $scope.description = "I like long walks on the beach..."

        var testData = [
            {
                "_id": 1,
                "type": "image"
            },
            {
                "_id": 2,
                "type": "image"
            },
            {
                "_id": 3,
                "type": "album",
                "alb": [
                    {
                        "_id": 4,
                        "type": "image"
                    },
                    {
                        "_id": 5,
                        "type": "image"
                    },
                    {
                        "_id": 6,
                        "type": "image"
                    }
                ]
            },
            {
                "_id": 7,
                "type": "image"
            },
            {
                "_id": 8,
                "type": "image"
            },
            {
                "_id": 9,
                "type": "image"
            },
            {
                "_id": 10,
                "type": "album",
                "alb": [
                    {
                        "_id": 11,
                        "type": "image"
                    },
                    {
                        "_id": 12,
                        "type": "image"
                    }
                ]
            },
            {
                "_id": 13,
                "type": "image"
            },
            {
                "_id": 14,
                "type": "image"
            },
            {
                "_id": 15,
                "type": "image"
            }
        ];

        console.log(testData);

        $scope.testData = testData;
    }]);

instaFlickControllers.controller('PictureCtrl', ['$scope',
    function ($scope) {

        var testPictureData = {
            "_id": 1,
            "path": "http://lorempixel.com/600/300/fashion/",
            "likes": 18,
            "description": "This is a cool fashion image!",
            "date": "2016-03-09"
        };

        var testCommentData = [
            {
                "_id": 1,
                "text": "This is a comment",
                "likes": 41,
                "date": "2016-03-11"
            },
            {
                "_id": 2,
                "text": "This is another comment",
                "likes": 0,
                "date": "2016-03-10"
            },
            {
                "_id": 3,
                "text": "This is the third comment.",
                "likes": 2,
                "date": "2016-03-09"
            }
        ];

        $scope.image = testPictureData;
        $scope.comments = testCommentData;

    }]);