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
        $mdDateLocaleProvider.months =['Ianuarie', 'Februarie', 'Martie', 'Aprilie', 'Mai', 'Iunie',
            'Iulie', 'August', 'Septembrie', 'Octombrie', 'Noiembrie', 'Decembrie'];
        $mdDateLocaleProvider.shortMonths =['Ian', 'Feb', 'Mar', 'Apr', 'Mai', 'Iun',
            'Iul', 'Aug', 'Sep', 'Oct', 'Noi', 'Dec'];
        $mdDateLocaleProvider.days = ['Luni', 'Marti', 'Miercuri', 'Joi', 'Vineri', 'Sambata', 'Duminica'];
        $mdDateLocaleProvider.days = ['Lu', 'Ma', 'Mi', 'Joi', 'Vi', 'Sam', 'Dum'];
        $mdDateLocaleProvider.firstDayOfWeek = 1;

        $mdDateLocaleProvider.formatDate = function(date) {
            return date ? moment(date).format('DD/MM/YYYY') : '';
        };

        $mdDateLocaleProvider.parseDate = function(dateString) {
            var m = moment(dateString, 'DD/MM/YYYY', true);
            return m.isValid() ? m.toDate() : new Date(NaN);
        };
        var path = 'app/app-pages/';
        $routeProvider
            .when('/login', {
                templateUrl: path + 'login/login.view.html',
            })

            .when('/register', {
                templateUrl: path + 'register/register.view.html',
            })
            .when('/', {
                templateUrl: path + 'home/home.view.html',
            })
            .when('/editUser', {
                templateUrl: path + 'users/edit-user/editUser.view.html',
            })
            .when('/adaugareAngajat', {
                templateUrl: path + 'adaugare-angajat/adaugare.angajat.html',
            })
            .when('/datePersonale', {
                templateUrl: path + 'date-personale/date.personale.html',
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

            //flagul evidentierea meniului activ
            $rootScope.isActiveNavBar = function (viewLocation) {
                var active = (viewLocation === $location.path());
                return active;
            };

        });
    }

})();