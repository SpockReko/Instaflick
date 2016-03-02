'use strict';

/* Services */

var productCatalogueService = angular.module('ProductCatalogueService', []);
var userRegistryService = angular.module('UserRegistryService', []);

// Representing the remote RESTful ProductCatalogue
userRegistryService.factory('UserRegistryProxy', ['$http',
    function($http) {

        var url = '/reg';

  
        return {
            findAll: function() {
                return $http.get(url);
            },
            findRange: function(first, max) {
                return $http.get(url + "/range?fst=" + first + "&max=" + max);
            },
            find: function(id) {
                return $http.get(url + "/" + id);
            },
            update: function(id, name, price) {
                var data = $.param({
                    name: name,
                    price: price
                }); 
                return $http.put(url + "/" + id +'?'+ data);;
            },
            create: function(username, password) {
                var data = $.param({
                    username: username,
                    password: password
                });                 
                return $http.post(url +'?'+ data);;
            },
            delete: function(id) {
                return $http.delete(url + "/" + id);
            },
            count: function() {
                return $http.get(url + "/count");
            }
        };
    }
]);