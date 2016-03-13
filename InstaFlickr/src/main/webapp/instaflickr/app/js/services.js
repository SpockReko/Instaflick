'use strict';

/* Services */

var userRegistryService = angular.module('UserRegistryService', []);
var mediaService = angular.module('MediaService', []);

// Representing the remote RESTful ProductCatalogue
userRegistryService.factory('UserRegistryProxy', ['$http',
    function ($http) {

        var url = 'http://localhost:8080/InstaFlickr/webresources/reg';

        return {
            create: function (username, password, repeatPassword) {
                console.log("create in UserRegistryProxy: " + username
                        + " " + password + " " + repeatPassword);

                var data = $.param({
                    username: username,
                    password: password,
                    repeatPassword: repeatPassword
                });
                return $http.post(url + '?' + data);
            },
            setupProfle: function (email, fname, lname, description) {
                console.log("Setting up profile in UserRegistryProxy: "
                        + email + " " + fname + " " + lname + " " + description);
                return $http.get(url + "/setup?" + "email=" + email

                        + "&fname=" + fname
                        + "&lname=" + lname
                        + "&description=" + description);

            },
            updateProfile: function (email, fname, lname, description) {
                console.log("Updating profile");
                return $http.get(url + "/update?" + "email=" + email
                        + "&fname=" + fname
                        + "&lname=" + lname
                        + "&description=" + description);
            },
            login: function (username, password) {
                console.log("login UserRegistryProxy: " + username + " " + password);
                return $http.get(url + "?username=" + username + "&password=" + password);
            },
            logout: function () {
                console.log("Logging out in UserRegistryProxy");
                return $http.get(url + "/logout");
            },
            getSession: function () {
                console.log("Getting session in UserRegistryProxy");

                return $http.get(url + "/session");
            },
            isLoggedIn: function () {
                console.log("Checking session in UserRegistryProxy");
                return $http.get(url + "/loggedIn");
            },
            getUserProfile: function (username) {
                console.log("getUserProfile in UserRegistryProxy");
                return $http.get(url + "/userprofile" + "?username=" + username);
            }
        };
    }
]);

mediaService.factory('MediaProxy', ['$http',
    function ($http) {

        var url = 'http://localhost:8080/InstaFlickr/webresources/media';

        return {
            getImage: function (pictureId) {
                console.log("Get image in MediaProxy");
                return $http.get(url + "/picture" + "?pictureId=" + pictureId);
            },
            getProfilePicture: function (username) {
                console.log("getProfilePicture in MediaProxy");
                return $http.get(url + "/profile-image" + "?username=" + username);
            },
            getProfileImages: function (username) {
                console.log("getProfileImages in MediaProxy");
                return $http.get(url + "?username=" + username);
            },
            getAlbums: function () {
                console.log("Getting albums in MediaProxy");
                return $http.get(url + "/albums");
            },
            getAlbumPictures: function (username, albumName) {
                console.log("getAlbumPictures");
                return $http.get(url + "/album-pictures?username=" + username + "&albumName=" + albumName);
            },
            createAlbum: function (albumName) {
                console.log("Creating album in MediaProxy: " + albumName);
                var data = $.param({
                    albumName: albumName
                });
                return $http.post(url + '/album?' + data);
            },
            addPictureToAlbum: function (pictureID, albumName) {
                console.log("Adding picture to album in MediaProxy: " + pictureID + " " + albumName);
                return $http.get(url + "?albumName=" + albumName + "&pictureID=" + pictureID);
            },
            updateLike: function (username, pictureId) {
                console.log("Update picture in MediaProxy from User: " + username);
                return $http.get(url + "/updateLike?username=" + username + "&pictureId=" + pictureId);
            },
            getAllMedia: function () {
                console.log("Getting all media in MediaProxy")
                return $http.get(url + "/media");
            },
            getFeed: function (username) {
                console.log("Getting feed for " + username)
                return $http.get(url + "/feed?username=" + username);
            },
            addComment: function (picture, acomment) {
                console.log("addComment in PictureProxy: " + picture + " " + acomment);
                var data = $.param({comment: acomment, picture: picture});
                return $http.post(url + "/comment?" + data);
            },
            getComments: function (picture) {
                console.log("getComments in Pictureproxy for picture: " + picture);
                var data = $.param({picture: picture});
                return $http.get(url + "/comments?" + data);
            }
        };
    }
]);

