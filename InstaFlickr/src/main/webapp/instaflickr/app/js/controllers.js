'use strict';

var instaFlickControllers = angular.module('InstaFlickControllers', []);

instaFlickControllers.controller('IndexCtrl',
        ['$scope', '$location', 'UserRegistryProxy',
            function ($scope, $location, UserRegistryProxy) {
                
                $scope.logout = function () {
                    console.log("User trying to logout IndexCtrl")
                    UserRegistryProxy.logout()
                            .success(function () {
                                console.log("Success logout!");
                                $location.path('/login');
                            }).error(function (data, status) {
                                console.log("Error in save RegisterCtrl status: " + status);
                                
                            });
                };
            }
        ]
);

// Log in controller
instaFlickControllers.controller('LoginCtrl',
        ['$scope', '$location', 'UserRegistryProxy',
            function ($scope, $location, UserRegistryProxy) {

                $scope.login = function () {
                    console.log("User trying to login LoginCtrl: " + $scope.user.username + " " + $scope.user.password);
                    UserRegistryProxy.login($scope.user.username, $scope.user.password)
                            .success(function () {
                                console.log("Success!");
                                $location.path('/profile');
                            }).error(function (data, status) {
                        console.log("Error in save RegisterCtrl status: " + status);
                        if (status === 409) {
                            $scope.user.msg = "Username is not registered";
                        }
                        if (status === 406) {
                            $scope.user.msg = "Incorrect password";
                        }
                    });
                };
            }
        ]);
        

// Register user controller
instaFlickControllers.controller('RegisterCtrl',
        ['$scope', '$location', 'UserRegistryProxy',
            function ($scope, $location, UserRegistryProxy) {

                $scope.save = function () {
                    console.log("Saving user in RegisterCtrl: " + $scope.user.username + " " + $scope.user.password + " " + $scope.user.repeatPassword);
                    UserRegistryProxy.create($scope.user.username, $scope.user.password, $scope.user.repeatPassword)
                            .success(function () {
                                console.log("Success!");
                                $location.path('/setupProfile');
                            }).error(function (data, status) {
                        console.log("Error in save RegisterCtrl status: " + status);
                        if (status === 409) {
                            $scope.user.msg = "Already registered user";
                        }
                        if (status === 406) {
                            $scope.user.msg = "The password entries do not match";
                        }
                    });
                };

                $scope.goBack = function () {
                    window.history.back();
                }
            }
        ]);

// SetupProfile user controller
instaFlickControllers.controller('SetupProfileCtrl',
        ['$scope', '$location', 'UserRegistryProxy',
            function ($scope, $location, UserRegistryProxy) {

                getSession($scope, $location, UserRegistryProxy);


                $scope.setupProfile = function () {
                    console.log("Setting up user profile in RegisterCtrl: " + $scope.user.email
                            + " " + $scope.user.fname + " " + $scope.user.lname + " " + $scope.user.description);

                    UserRegistryProxy.setupProfle($scope.user.email, $scope.user.fname, $scope.user.lname, $scope.user.description)
                            .success(function () {
                                console.log("Success!");
                                $location.path('/profile');
                            }).error(function (data, status) {
                        console.log("Error in save RegisterCtrl status: " + status);
                    });
                };

                $scope.goBack = function () {
                    window.history.back();
                }
            }
        ]);

// Profile controller
instaFlickControllers.controller('ProfileCtrl', ['$scope', '$location', 'UserRegistryProxy',
    function ($scope, $location, UserRegistryProxy) {

        console.log("ProfileCtrl checking that the user is logged in.");
        getSession($scope, $location, UserRegistryProxy);

        $scope.description = "I like long walks on the beach...";

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

instaFlickControllers.controller('signedinCtrl', ['$scope', 'UserRegistryProxy',
        function ($scope, UserRegistryProxy) {


        function signedIn($scope, UserRegistryProxy) {
        UserRegistryProxy.signedIn()
                .success(function(boolean) {
                console.log("get boolean from somethere: " + boolean['session']);
                        $scope.isLoggedIn;
                })
                .error(function(data, status) {
                console.log("Error in checking session in ProfileCtrl: " + status);
                })
                }

                }]);


// Helper functions
                function getSession($scope, $location, UserRegistryProxy) {
                UserRegistryProxy.getSession()
                        .success(function(json) {
                        console.log("Session retrieved in ProfileCtrl: " + json['username']);
                                $scope.name = json['username'];
                        })
                        .error(function(data, status) {
                        console.log("Error in checking session in ProfileCtrl: " + status);
                                if (status === 406) {
                        $location.path('/login');
                        }
                        });
                        }
