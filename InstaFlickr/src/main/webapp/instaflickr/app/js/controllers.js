'use strict';

var instaFlickControllers = angular.module('InstaFlickControllers', []);

// Feed controller
instaFlickControllers.controller('FeedCtrl', ['$scope', '$location', 'MediaProxy', '$stateParams', 'UserRegistryProxy',
    function ($scope, $location, MediaProxy, $stateParams, UserRegistryProxy) {


        UserRegistryProxy.getSession()
                .success(function () {
                    MediaProxy.getAllMedia()
                            .success(function (data) {
                                console.log("Got all media " + data);
                                $scope.data = data;
                            })
                            .error(function (data, status) {
                                console.log("Error in getting all media in FeedCtrl: " + status);
                            })
                })
                .error(function (data, status) {
                    if (status === 406) {
                        $location.path('/login');
                    } else {
                        console.log("Error in checking session in ProfileCtrl: " + status);
                    }
                });
    }
]);

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
                        } else if (status === 406) {
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
                            $scope.msg = "Already registered user";
                        } else if (status === 406) {
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
        ['$scope', '$location', '$timeout', 'Upload', 'UserRegistryProxy',
            function ($scope, $location, $timeout, Upload, UserRegistryProxy) {

                getSession($location, UserRegistryProxy);


                $scope.setupProfile = function (image) {
                    console.log("Setting up user profile in RegisterCtrl: " + $scope.user.username + " " + $scope.user.fname + " " + $scope.user.lname + " " + $scope.user.description);

                    UserRegistryProxy.setupProfle($scope.user.username, $scope.user.fname, $scope.user.lname, $scope.user.description)
                            .success(function () {
                                console.log("Success!");
                                $location.path('/profile');
                            }).error(function (data, status) {
                        console.log("Error in save RegisterCtrl status: " + status);
                    });
                    image.upload = Upload.upload({
                        url: 'http://localhost:8080/InstaFlickr/webresources/media/profile-image',
                        data: {file: image}
                    });

                    image.upload.then(function (response) {
                        $timeout(function () {
                            image.result = response.data;
                            console.log(response.data);
                            //$scope.upImg = response.data;
                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = "Server Error! (" + response.data + ")";
                    }, function (evt) {
                        // Math.min is to fix IE which reports 200% sometimes
                        image.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
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

        if ($stateParams.username) {
            getProfilePicture($stateParams.username, MediaProxy, $scope)
            getProfileImages($stateParams.username, MediaProxy, $scope)
        } else {
            UserRegistryProxy.getSession()
                    .success(function (json) {
                        console.log("Get profile images: " + json['username']);
                        getProfilePicture(json['username'], MediaProxy, $scope)
                        getProfileImages(json['username'], MediaProxy, $scope)
                    })
                    .error(function (data, status) {
                        if (status === 406) {
                            $location.path('/login');
                        } else {
                            console.log("Error in checking session in ProfileCtrl: " + status);
                        }
                    });
        }
    }
]);

instaFlickControllers.controller('PictureCtrl', ['$scope', '$stateParams', 'MediaProxy',
    function ($scope, $stateParams, MediaProxy) {

        MediaProxy.getImage($stateParams.id).success(function (data) {
            console.log("Success!");
            console.log(data);

            $scope.image = data;
        });
    }]);

instaFlickControllers.controller('UploadCtrl',
        ['$scope', '$location', '$timeout', 'Upload', 'MediaProxy', 'UserRegistryProxy', '$state',
            function ($scope, $location, $timeout, Upload, MediaProxy, UserRegistryProxy, $state) {

                getSession($location, UserRegistryProxy);

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

                $scope.uploadPic = function (file) {
                    console.log("uploadPic() called");
                    console.log(file);
                    var albumName = "";
                    if ($scope.selectedAlbum !== undefined) {
                        albumName = $scope.selectedAlbum;
                    }
                    uploadPicture($scope, $timeout, Upload, file, albumName);
                }
            }
        ]);

instaFlickControllers.controller('AlbumCtrl', ['$scope', '$stateParams', 'MediaProxy',
    function ($scope, $stateParams, MediaProxy) {
        console.log("AlbumCtrl");

        $scope.albumName = $stateParams.albumname;
        $scope.owner = $stateParams.username;

        MediaProxy.getAlbumPictures($stateParams.username, $stateParams.albumname).success(function (data) {
            $scope.album = data;
        });
    }
]);

// Helper functions
function getSession($location, UserRegistryProxy) {
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

function uploadPicture($scope, $timeout, Upload, image, albumName) {
    image.upload = Upload.upload({
        url: 'http://localhost:8080/InstaFlickr/webresources/media',
        data: {file: image, albumName: albumName}
    });

    image.upload.then(function (response) {
        $timeout(function () {
            image.result = response.data;
            console.log(response.data);
            //$scope.upImg = response.data;
        });
    }, function (response) {
        if (response.status > 0)
            $scope.errorMsg = "Server Error! (" + response.data + ")";
    }, function (evt) {
        // Math.min is to fix IE which reports 200% sometimes
        image.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
    });
}

function getUserProfile(username, UserRegistryProxy, $scope) {
    console.log("Get user profile: " + username);
    UserRegistryProxy.getUserProfile(username).success(function (data) {
        console.log(data);
        console.log("Success! user profile");
        $scope.profileData = data;
    });
}

function getProfileImages(userName, MediaProxy, $scope) {
    console.log("Get profile images: " + userName);
    MediaProxy.getProfileImages(userName).success(function (data) {
        console.log(data);
        console.log("Success!");
        $scope.data = data;
    });
}
function getProfilePicture(userName, MediaProxy, $scope) {
    console.log("Get profile picture: " + userName);
    MediaProxy.getProfilePicture(userName).success(function (data) {
        console.log(data);
        console.log("Success!");
        $scope.profilePicture = data['image'];
    });
}

function getProfileImagesSorted(userName, MediaProxy, $scope) {
    console.log("Get profile images sorted: " + userName);
    MediaProxy.getProfileImages(userName).success(function (data) {
        console.log(data);
        console.log("Success!");

        $scope.getOrderedData = function () {
            return data.sort(compare);
        };

        var compare = function (a, b) {
            if (parseInt(a.time) > parseInt(b.time))
                return -1;
            if (parseInt(a.time) < parseInt(b.time))
                return 1;
            return 0;
        };

        $scope.data = $scope.getOrderedData();
    });
}