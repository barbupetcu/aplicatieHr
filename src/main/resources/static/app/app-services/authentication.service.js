﻿(function () {
    'use strict';

    angular
        .module('app')
        .factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$http', '$cookies', '$rootScope', '$timeout', 'UserService'];
    function AuthenticationService($http, $cookies, $rootScope, $timeout, UserService) {
        var service = {};

        service.Login = Login;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;

        return service;
        
        function Login(username, password, callback) {

        	$http({
                url: '/authenticate',
                method: "POST",
                params: {
                    username: username,
                    password: password
                }
            }).then(function successCallback(response) {
                	callback(response);
                	}); 

        }

        function SetCredentials(response) {

            var isManager = false;
            if (response.roles.indexOf("ROLE_MANAGER")>=0){
                isManager = true;
            }

            $rootScope.globals = {
                currentUser: {
                    username: response.username,
                    authdata: response.token,
                    name: response.name,
                    lastName: response.lastName,
                    dept: response.dept,
                    id: response.id,
                    roles: response.roles,
                    isManager: isManager
                }
            };
 
            // se adauga tokenul generat in headerul urmatoarelor requesturi
            $http.defaults.headers.common['Authorization'] = 'Bearer ' + response.token;
 
            // se adauga detaliile userului in cookies pana cand userul se delogheaza sau pana cand acestea expira
            var cookieExp = new Date();
            cookieExp.setDate(cookieExp.getDate() + 7);
            $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
        }

        function ClearCredentials() {
            $rootScope.globals = {};
            $cookies.remove('globals');
            $http.defaults.headers.common.Authorization = 'Bearer ';
        }
        
    }
     
})();