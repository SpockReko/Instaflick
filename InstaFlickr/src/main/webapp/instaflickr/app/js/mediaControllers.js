'use strict';

var mediaControllers = angular.module('MediaControllers', []);

// Controller for feed.html
mediaControllers.controller('FeedCtrl', ['$scope', '$location', 'MediaProxy', 'UserRegistryProxy',
    function ($scope, $location, MediaProxy, UserRegistryProxy) {

        UserRegistryProxy.getSession() // First check if someone is logged in.
                .success(function (json) {
                    MediaProxy.getFeed(json['username']) // If someone is logged in, get the feed for that person
                            .success(function (data) {
                                console.log("Got all media " + data);
                                console.log(data);
                                sortMedia(data, $scope);
                            })
                            .error(function (data, status) {
                                console.log("Error in getting all media in FeedCtrl: " + status);
                            });
                })
                .error(function (data, status) { // If not logged in, send to login page
                    if (status === 406) {
                        $location.path('/login');
                    } else {
                        console.log("Error in checking session in FeedCtrl: " + status);
                    }
                });
    }
]);

// Controller for profile.html
mediaControllers.controller('ProfileCtrl', ['$scope', '$location', 'MediaProxy', '$stateParams', 'UserRegistryProxy',
    function ($scope, $location, MediaProxy, $stateParams, UserRegistryProxy) {

        console.log("Getting profile information in ProfileCtrl");
        if ($stateParams.username !== undefined) { // Display other profiles page
            console.log("Other users profile: " + $stateParams.username);
            getProfile($stateParams.username, UserRegistryProxy, MediaProxy, $scope);
        } else { // Display own profile page
            console.log("Logged in users profile");
            UserRegistryProxy.getSession()
                    .success(function (json) {
                        console.log("Got profile " + json['username']);
                        getProfile(json['username'], UserRegistryProxy, MediaProxy, $scope);
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

// Controller for picture.html
mediaControllers.controller('PictureCtrl', ['$scope', '$stateParams', 'MediaProxy', 'UserRegistryProxy',
    function ($scope, $stateParams, MediaProxy, UserRegistryProxy) {

        MediaProxy.getImage($stateParams.id).success(function (data) {
            console.log("Success with getImage!");
            console.log(data);
            $scope.image = data;
            $scope.id = $stateParams.id;
            $scope.updateComments();
        });

        // Get the profile picture to display as avatar for comments
        UserRegistryProxy.getSession()
                .success(function (json) {
                    getProfilePicture(json['username'], MediaProxy, $scope)
                });
                
        getPictureComments($stateParams.id, MediaProxy, $scope);

        $scope.updateLikes = function () {
            UserRegistryProxy.getSession().success(function (data) {
                console.log("User pressing like button: " + data['username']);
                MediaProxy.updateLike(data['username'], $scope.id)
                        .success(function (data) {
                            console.log("Success with updateLikes in PictureCtrl!");
                            $scope.image.likes = data['likes'];
                        }).error(function (data, status) {
                    console.log("Error in updateLikes in PictureCtrl");
                });
            });
        };

        $scope.postComment = function () {
            console.log("Posting a comment in PictureCtrl, picID: " + $stateParams.id + " comment: " + $scope.formData.comment);
            MediaProxy.addComment($stateParams.id, $scope.formData.comment)
                    .success(function () {
                        console.log("Comment is successfully posted!");
                        getPictureComments($stateParams.id, MediaProxy, $scope);
                    });
        };

    }
]);

// Controller for upload.html
mediaControllers.controller('UploadCtrl',
        ['$scope', '$location', '$timeout', 'Upload', 'MediaProxy', 'UserRegistryProxy', '$state',
            function ($scope, $location, $timeout, Upload, MediaProxy, UserRegistryProxy, $state) {

                getSession($location, UserRegistryProxy);
                
                // Getting the users albums
                MediaProxy.getAlbums()
                        .success(function (data) {
                            console.log("Success! " + data[0].albumName);
                            $scope.albums = data;
                        })
                        .error(function (data, error) {
                            console.log("Error in getAlbum in UploadCtrl status: " + status);
                        });

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
                            });
                };

                $scope.uploadPic = function (file) {
                    console.log("uploadPic() called");
                    console.log(file);
                    var albumName = "";
                    if ($scope.selectedAlbum !== undefined) {
                        albumName = $scope.selectedAlbum;
                    }
                    uploadPicture($scope, $timeout, Upload, file, albumName, $scope.inputDescription);
                };
            }
        ]);

// Controller for album.html
mediaControllers.controller('AlbumCtrl', ['$scope', '$stateParams', 'MediaProxy',
    function ($scope, $stateParams, MediaProxy) {
        
        console.log("AlbumCtrl");
        $scope.albumName = $stateParams.albumname;
        $scope.owner = $stateParams.username;
        
        MediaProxy.getAlbumPictures($stateParams.username, $stateParams.albumname).success(function (data) {
            console.log("Success with getAlbumPictures!");
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

function uploadPicture($scope, $timeout, Upload, image, albumName, description) {
    image.upload = Upload.upload({
        url: 'http://localhost:8080/InstaFlickr/webresources/media',
        data: {file: image, albumName: albumName, description: description}
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

function getProfile(username, UserRegistryProxy, MediaProxy, $scope) {
    console.log("Getting profile: " + username);
    getUserProfile(username, UserRegistryProxy, $scope);
    getProfilePicture(username, MediaProxy, $scope);
    MediaProxy.getProfileImages(username, MediaProxy, $scope)
            .success(function (data) {
                console.log(data);
                console.log("Success with getProfile!");
                console.log("Get profile images sorted");
                sortMedia(data, $scope);
            });
}

function getUserProfile(username, UserRegistryProxy, $scope) {
    console.log("Get user profile: " + username);
    UserRegistryProxy.getUserProfile(username).success(function (data) {
        console.log(data);
        console.log("Success with getUserProfile");
        $scope.profileData = data;
    });
}

function getProfileImages(userName, MediaProxy, $scope) {
    console.log("Get profile images: " + userName);
    MediaProxy.getProfileImages(userName).success(function (data) {
        console.log(data);
        console.log("Success with getProfileImage");
        $scope.data = data;
    });
}
function getProfilePicture(userName, MediaProxy, $scope) {
    console.log("Get profile picture: " + userName);
    MediaProxy.getProfilePicture(userName).success(function (data) {
        console.log(data);
        console.log("Success with getProfilePicture");
        $scope.profilePicture = data['image'];
    });
}

function sortMedia(media, $scope) {
    $scope.getOrderedData = function () {
        return media.sort(compare);
    };
    var compare = function (a, b) {
        if (parseInt(a.time) > parseInt(b.time))
            return -1;
        if (parseInt(a.time) < parseInt(b.time))
            return 1;
        return 0;
    };
    $scope.data = $scope.getOrderedData();
}

function getPictureComments(id, MediaProxy, $scope) {
    console.log("Getting comments in PictureCtrl for picture! " + id);
    MediaProxy.getComments(id)
            .success(function (data) {
                console.log("Got comments!");
                console.log(data);
                $scope.comments = data;
            }).
            error(function (error) {
                console.log("Could not get comments! " + error);
            });
}