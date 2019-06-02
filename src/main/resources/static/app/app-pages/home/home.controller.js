(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['UserService', '$rootScope','$timeout'];
    function HomeController(UserService, $rootScope,$timeout) {
        var vm = this;
        vm.currentUser={};
        vm.contracteNoi = [];
        vm.titlu='Contracte Noi';
        vm.dataLoading = true;


        (function initController() {


            $timeout(clearLoading, 2000);
        })();

        vm.loadContracteNoi = function loadContracteNoi(){
            vm.titlu='Contracte Noi';
            vm.dataLoading = true;

            $timeout(clearLoading, 2000);

        }

        vm.loadAngajatiPlecati = function loadAngajatiPlecati(){
            vm.titlu='Angajati care vor pleca';
            vm.dataLoading = true;

            $timeout(clearLoading, 2000);
        }

        vm.newUsers = function newUsers(){
            vm.titlu='Utilizatori de aprobat';
            vm.dataLoading = true;

            $timeout(clearLoading, 2000);
        }

        vm.loadTaskuri = function loadTaskuri(){
            vm.titlu='Taskuri';
            vm.dataLoading = true;

            $timeout(clearLoading, 2000);
        }

        function clearLoading(){
            vm.dataLoading = false;
        }



    }

})();