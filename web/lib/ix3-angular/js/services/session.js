"use strict";
/**
 * Servicio para gestionar la sesión en el servidor
 */
angular.module("es.logongas.ix3").service("session", ['$http','notify','apiurl','$q', function($http,notify,apiurl,$q) {
        return {
            login: function(login, password) {
                var deferred = $q.defer();
                var config = {
                    method: 'POST',
                    url: apiurl + '/session',
                    data:jQuery.param({
                        login: login,
                        password: password
                    }),
                    headers:{
                        'Content-Type':'application/x-www-form-urlencoded'
                    }
                };

                $http(config).success(function(data, status, headers, config) {
                    deferred.resolve(data);
                }).error(function(data, status, headers, config) {
                    if (status===400) {
                        deferred.reject(data);
                    } else {
                        throw new Error("Fallo al obtener los datos:"+status+"\n"+data);
                    }                   
                });
                
                return deferred.promise;
            },
            logout: function() {
                var deferred = $q.defer();
                
                var config = {
                    method: 'DELETE',
                    url: apiurl + '/session'
                };

                $http(config).success(function(data, status, headers, config) {
                    deferred.resolve(data);
                }).error(function(data, status, headers, config) {
                    if (status===400) {
                        deferred.reject(data);
                    } else {
                        throw new Error("Fallo al obtener los datos:"+status+"\n"+data);
                    } 
                });
                
                return deferred.promise;
            },
            logged: function() {
                var deferred = $q.defer();
                
                var config = {
                    method: 'GET',
                    url: apiurl + '/session'
                };

                $http(config).success(function(data, status, headers, config) {
                    deferred.resolve(data);
                }).error(function(data, status, headers, config) {
                    if (status===400) {
                        deferred.reject(data);
                    } else {
                        throw new Error("Fallo al obtener los datos:"+status+"\n"+data);
                    } 
                });
                
                return deferred.promise;
            }
        };
    }]);