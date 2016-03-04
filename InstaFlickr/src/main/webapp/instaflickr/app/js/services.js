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
                console.log("create in UserRegistryProxy: " + username + " " + password + " " + repeatPassword)
                var data = $.param({
                    username: username,
                    password: password,
                    repeatPassword: repeatPassword
                });
                return $http.post(url + '?' + data);
            },
            login: function (username, password) {
                console.log("login UserRegistryProxy: " + username + " " + password)
                return $http.get(url + "?username=" + username + "&password=" + password);
            }
        };
    }
]);

mediaService.factory('MediaProxy', ['$http',
    function ($http) {

        var url = 'http://localhost:8080/InstaFlickr/webresources/media';


        return {
            getImage: function () {
                console.log("Get image in MediaProxy");
                return $http.get(url);
            }
        };
    }
]);