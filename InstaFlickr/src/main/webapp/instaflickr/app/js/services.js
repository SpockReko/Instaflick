'use strict';

/* Services */

var userRegistryService = angular.module('UserRegistryService', []);
var mediaService = angular.module('MediaService', []);

// Representing the remote RESTful ProductCatalogue
userRegistryService.factory('UserRegistryProxy', ['$http',
    function ($http) {

        var url = 'http://localhost:8080/InstaFlickr/webresources/reg';

        return {
            create: function(username, password, repeatPassword) {
                console.log("create in UserRegistryProxy: " + username 
                        + " " + password + " " + repeatPassword)
                var data = $.param({
                    username: username,
                    password: password,
                    repeatPassword: repeatPassword
                });
                return $http.post(url + '?' + data);
            },
            setupProfle: function(email, fname, lname, description) {
                console.log("Setting up profile in UserRegistryProxy: " 
                        + email + " " + fname + " " + lname + " " + description)
                return $http.get(url + "/setup?" + "email=" + email 
                                                 + "&fname=" + fname 
                                                 + "&lname=" + lname 
                                                 + "&description=" + description);

            },
            login: function(username, password) {
                console.log("login UserRegistryProxy: " + username + " " + password)
                return $http.get(url + "?username=" + username + "&password=" + password);
            },
            
            logout: function(){
                console.log("logout UserRegistryProxy")
                return $http.get(url + "/logout");
            },
            
            getSession: function() {
                console.log("Getting session in UserRegistryProxy")
                return $http.get(url + "/session");
            },
            
            signedIn: function() {
                console.log("getting boolean from index to logedin")
                return $http.get(url + "/session");
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
            },
            getMany: function(username) {
                console.log("getMany in MediaProxy");
                return $http.get(url + "?username=" + username);
            }
        };
    }
]);