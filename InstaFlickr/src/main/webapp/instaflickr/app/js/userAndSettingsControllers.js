'use strict';

var userAndSettingsControllers = angular.module('UserAndSettingsControllers', []);

// Controller for index.html
userAndSettingsControllers.controller('IndexCtrl', ['$scope', '$location', 'UserRegistryProxy', '$window',
    function ($scope, $location, UserRegistryProxy, $window) {

        console.log("Checking if logged in");
        UserRegistryProxy.isLoggedIn() // Changing the name of the sign in/out button.
                .success(
                        function (data) {
                            if (data['loggedIn']) {
                                console.log("You are logged in");
                                $scope.signedIn = " Sign Out";
                            } else {
                                console.log("You are not logged in");
                                $scope.signedIn = " Sign In";
                                $location.path('/signin');
                            }
                        }
                )
                .error(function (data, status) {
                    console.log("Error in isLoggedIn IndexCtrl status: " + status);
                });

        $scope.signInOut = function () {
            if ($scope.signedIn === " Sign In") {
                console.log("Sign in");
                $location.path('/login');
            } else if ($scope.signedIn === " Sign Out") {
                console.log("Sign out");
                UserRegistryProxy.logout()
                        .success(
                                function () {
                                    console.log("Successfully logged out!");
                                    $location.path('/home');
                                    $window.location.reload();
                                }
                        )
                        .error(function (data, status) {
                            console.log("Error in save RegisterCtrl status: " + status);
                        });
            }
        };
    }]);

// Controller for login.html
userAndSettingsControllers.controller('LoginCtrl',
        ['$scope', '$location', 'UserRegistryProxy', '$window',
            function ($scope, $location, UserRegistryProxy, $window) {

                $scope.login = function () {
                    console.log("User trying to login LoginCtrl: " + $scope.user.username + " " + $scope.user.password);
                    UserRegistryProxy.login($scope.user.username, $scope.user.password)
                            .success(function () {
                                console.log("Success with login!");
                                $scope.signedIn = true;
                                $location.path('/');
                                $window.location.reload(); // Reload to change the sign in/out button. Goes to feed.
                            }).error(function (data, status) {
                        if (status === 409) {
                            $scope.user.msg = "Username is not registered";
                        } else if (status === 406) {
                            $scope.user.msg = "Incorrect password";
                        } else {
                            console.log("Error in login LoginCtrl status: " + status);
                        }
                    });
                };
            }
        ]);
        
// Controller for register.html
userAndSettingsControllers.controller('RegisterCtrl',
        ['$scope', '$location', 'UserRegistryProxy',
            function ($scope, $location, UserRegistryProxy) {

                $scope.save = function () {
                    console.log("Saving user in RegisterCtrl: " + $scope.user.username + " " + $scope.user.password + " " + $scope.user.repeatPassword);
                    UserRegistryProxy.create($scope.user.username, $scope.user.password, $scope.user.repeatPassword)
                            .success(function () {
                                console.log("Success with create!");
                                $scope.signedIn = true;
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
                };
            }
        ]);
        
// Controller for setupProfile.html
userAndSettingsControllers.controller('SetupProfileCtrl',
        ['$scope', '$location', '$timeout', 'Upload', 'UserRegistryProxy', '$window',
            function ($scope, $location, $timeout, Upload, UserRegistryProxy, $window) {

                getSession($location, UserRegistryProxy);
                
                $scope.setupProfile = function (image) {
                    console.log("Setting up user profile in RegisterCtrl: " + $scope.user.email + " " + $scope.user.fname + " " + $scope.user.lname + " " + $scope.user.description);
                    UserRegistryProxy.setupProfle($scope.user.email, $scope.user.fname, $scope.user.lname, $scope.user.description)
                            .success(function () {
                                console.log("Success with setupProfle!");
                                $location.path('/');
                                $window.location.reload(); // Relod to cahnge the sign in/out button. Goes to feed.
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
                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = "Server Error! (" + response.data + ")";
                    }, function (evt) {
                        image.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                };
                $scope.goBack = function () {
                    window.history.back();
                };
            }
        ]);

// Controller for settings.html
userAndSettingsControllers.controller('SettingsCtrl', ['$scope', '$location', '$timeout', 'UserRegistryProxy', 'Upload',
    function ($scope, $location, $timeout, UserRegistryProxy, Upload) {

        UserRegistryProxy.getSession()
                .success(function (json) {
                    console.log("Session retrieved in ProfileCtrl: " + json['username']);
                    getUserProfile(json['username'], UserRegistryProxy, $scope);
                })
                .error(function (data, status) {
                    if (status === 406) {
                        $location.path('/login');
                    } else {
                        console.log("Error in checking session in ProfileCtrl: " + status);
                    }
                });

        $scope.updateProfile = function (image) {
            console.log("Updating profile");

            if (!$scope.change) {
                console.log("No user info changes");
            } else {
                if (!$scope.change.email) {
                    $scope.change.email = $scope.profileData.email;
                }
                if (!$scope.change.fname) {
                    $scope.change.fname = $scope.profileData.fname;
                }
                if (!$scope.change.lname) {
                    $scope.change.lname = $scope.profileData.lname;
                }
                if (!$scope.change.description) {
                    $scope.change.description = $scope.profileData.description;
                }

                UserRegistryProxy.updateProfile($scope.change.email, $scope.change.fname,
                        $scope.change.lname, $scope.change.description);
            }

            if (!image) {
                console.log("No profile picture changes");
            } else {
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
            }
        }
    }
]);

// Controller for help.html
userAndSettingsControllers.controller('HelpCtrl', ['$scope',
    function ($scope) {
        var visible;

        var showme = function (elem) {
            if (visible) {
                console.log("hiding " + visible);
                document.getElementById(visible).style.display = "none";
            }
            if (elem) {
                console.log("showing " + elem);
                document.getElementById(elem).style.display = "block";
                visible = elem;
            }
        };

        $scope.reg = function () {
            showme("help-reg");
        };
        $scope.set = function () {
            showme("help-set");
        };
        $scope.log = function () {
            showme("help-log");
        };
        $scope.pic = function () {
            showme("help-pic");
        };
        $scope.alb = function () {
            showme("help-alb");
        };
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

function getUserProfile(username, UserRegistryProxy, $scope) {
    console.log("Get user profile: " + username);
    UserRegistryProxy.getUserProfile(username).success(function (data) {
        console.log(data);
        console.log("Success with getUserProfile");
        $scope.profileData = data;
    });
}