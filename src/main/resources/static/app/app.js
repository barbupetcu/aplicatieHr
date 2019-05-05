(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies', 'ui.bootstrap', 'ngMaterial', 'underscore', 'ngSanitize'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$mdDateLocaleProvider'];
    function config($routeProvider, $mdDateLocaleProvider) {
        /**
         * @param date {Date}
         * @returns {string} string representation of the provided date
         */
        $mdDateLocaleProvider.formatDate = function(date) {
            return date ? moment(date).format('DD MMM YYYY') : '';
        };

        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'app/app-deploy/home/ROLE_USER/home.view.html',
                controllerAs: 'vm'
            })
            .when('/homeManager', {
                controller: 'HomeControllerMan',
                templateUrl: 'app/app-deploy/home/ROLE_MANAGER/home.view.html',
                controllerAs: 'vm'
            })

            .when('/editUser', {
                controller: 'EditUserController',
                templateUrl: 'app/app-deploy/users/editUser.view.html',
                controllerAs: 'vm'
            })

            .when('/approveUser', {
                controller: 'AproveUserController',
                templateUrl: 'app/app-deploy/users/approveUser.view.html',
                controllerAs: 'vm'
            })

            .when('/task', {
                controller: 'TaskController',
                templateUrl: 'app/app-deploy/task/task.view.html',
                controllerAs: 'vm'
            })

            .when('/taskIteration', {
                controller: 'IterationController',
                templateUrl: 'app/app-deploy/task/taskIteration.view.html',
                controllerAs: 'vm'
            })

            .when('/descTask', {
                templateUrl: 'app/app-deploy/task/descTask.view.html'
            })

            .when('/descTask2', {
                templateUrl: 'app/app-deploy/task/descTask2.view.html'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'app/login/login.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'app/register/register.view.html',
                controllerAs: 'vm'
            })

            .otherwise({ redirectTo: '/login' });
    }

    run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
    function run($rootScope, $location, $cookies, $http) {
        // pastreaza userul logat dupa ce dam refresh la pagina (se recupereaza datele din cookies)
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Bearer ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            
            $rootScope.isRestrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;

            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;

            // ne redirectioneaza catre login daca nu suntem logati
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }

            if ($rootScope.globals.currentUser=== undefined) {
                $rootScope.globals.currentUser ={};
            }

            //ne redirectioneaza catre home-ul specific fiecarui tip de utilizator
            if(!restrictedPage && $rootScope.globals.currentUser.roles!= undefined){
                if ($rootScope.globals.currentUser.roles.indexOf("ROLE_MANAGER")>=0 && $location.path()==="/"){
                    $location.path('/homeManager');
                }
            }
            
            //flagul evidentierea meniului activ
            $rootScope.isActiveNavBar = function (viewLocation) {
                var active = (viewLocation === $location.path());
                return active;
            };

        });
    }

})();