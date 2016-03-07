'use strict';

var instaFlickControllers = angular.module('InstaFlickControllers', []);

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
                        if (status === 409) {
                            $scope.user.msg = "Username is not registered";
                        }
                        else if (status === 406) {
                            $scope.user.msg = "Incorrect password";
                        } else {
                            console.log("Error in save RegisterCtrl status: " + status);
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
                        if (status === 409) {
                            $scope.user.msg = "Already registered user";
                        }
                        else if (status === 406) {
                            $scope.user.msg = "The password entries do not match";
                        } else {
                            console.log("Error in save RegisterCtrl status: " + status);
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
                    console.log("Setting up user profile in RegisterCtrl: " + $scope.user.username + " " + $scope.user.fname + " " + $scope.user.lname + " " + $scope.user.description);

                    UserRegistryProxy.setupProfle($scope.user.username, $scope.user.fname, $scope.user.lname, $scope.user.description)
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
instaFlickControllers.controller('ProfileCtrl', ['$scope', '$location', 'MediaProxy', '$stateParams', 'UserRegistryProxy',
    function ($scope, $location, MediaProxy, $stateParams, UserRegistryProxy) {

        $scope.description = "I like long walks on the beach..."

        if ($stateParams.username) {

            console.log("Get profile images: " + $stateParams.username);

            MediaProxy.getProfileImages($stateParams.username).success(function (data) {
                console.log(data);
                console.log("Success!");
                $scope.data = data;
                //console.log("id" + data[0].id);
            });
        } else {


            UserRegistryProxy.getSession()
                    .success(function (json) {
                        console.log("Get profile images: " + json['username']);
                        MediaProxy.getProfileImages(json['username']).success(function (data) {
                            console.log(data);
                            console.log("Success!");
                            $scope.data = data;
                            console.log("id: " + data[0].id);
                        });
                    })
                    .error(function (data, status) {
                        if (status === 406) {
                            $location.path('/login');
                        } else {
                            console.log("Error in checking session in ProfileCtrl: " + status);
                        }
                    });
        }
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

instaFlickControllers.controller('PictureCtrl', ['$scope', '$stateParams', 'MediaProxy',
    function ($scope, $stateParams, MediaProxy) {

        MediaProxy.getImage($stateParams.id).success(function (data) {
            console.log("Success!");
            console.log(data);

            $scope.image = data;
        });
}]);

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


instaFlickControllers.controller('PictureCtrl', ['$scope', 'PictureProxy',
    function ($scope, PictureProxy) {
        $scope.image = testPictureData;
        $scope.comments = testCommentData;


        $scope.postComment = function(){
            testCommentData = [];
            $scope.comments = testCommentData;
            PictureProxy.addComment($scope.formData.picture,$scope.formData.comment);
        };
    }]);

instaFlickControllers.controller('UploadCtrl',
        ['$scope', '$location', '$timeout', 'Upload', 'MediaProxy', 'UserRegistryProxy', '$state',
            function ($scope, $location, $timeout, Upload, MediaProxy, UserRegistryProxy, $state) {
                
                getSession($scope, $location, UserRegistryProxy);
                
                MediaProxy.getAlbums()
                        .success(function (data) {
                            console.log("Success! " + data[0].albumName);
                            $scope.albums = data;
                        })
                        .error(function (data, error) {
                            console.log("Error in getAlbum in UploadCtrl status: " + status);
                        })


                $scope.returnPath = function () {
                    $scope.imagePath = "media/image1.png";
                };

                $scope.createAlbum = function () {
                    $scope.msg = "";
                    console.log("Creating album: " + $scope.album.name);
                    MediaProxy.createAlbum($scope.album.name)
                            .success(function () {
                                console.log("Success!");
                                $state.reload();
                            })
                            .error(function (data, status) {
                                if (status === 409) {
                                    $scope.msg = "You already have an album called " + $scope.album.name;
                                } else {
                                    console.log("Error in createAlbum in UploadCtrl status: " + status);
                                }
                            })
                };
                /*
                 $scope.getImage = function () {
                 console.log("UploadCtrl getImage");
                 MediaProxy.getImage()
                 .success(function (jsonObject) {
                 console.log("Success!");
                 $scope.imagePath = path;
                 }).error(function () {
                 console.log("getImage error");
                 });
                 };
                 */
                $scope.uploadPic = function (file) {
                    console.log("uploadPic() called");
                    console.log(file);
                    var albumName = "";
                    if ($scope.selectedAlbum !== undefined) {
                        albumName = $scope.selectedAlbum;
                    }
                    file.upload = Upload.upload({
                        url: 'http://localhost:8080/InstaFlickr/webresources/media',
                        data: {file: file,
                            albumName: albumName}
                    });

                    file.upload.then(function (response) {
                        $timeout(function () {
                            file.result = response.data;
                            console.log(response.data);
                            //$scope.upImg = response.data;
                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = "Server Error! (" + response.data + ")";
                    }, function (evt) {
                        // Math.min is to fix IE which reports 200% sometimes
                        file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                }
            }
        ]);

// Helper functions
function getSession($scope, $location, UserRegistryProxy) {
    UserRegistryProxy.getSession()
            .success(function (json) {
                console.log("Session retrieved in ProfileCtrl: " + json['username']);
                return json['username'];
            })
            .error(function (data, status) {
                if (status === 406) {
                    $location.path('/login');
                } else {
                    console.log("Error in checking session in ProfileCtrl: " + status);
                }
            });
}
