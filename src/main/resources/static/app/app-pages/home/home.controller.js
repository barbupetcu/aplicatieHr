(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['UserService', '$rootScope','$timeout','$http','FlashService'];
    function HomeController(UserService, $rootScope,$timeout,$http,FlashService) {
        var vm = this;
        vm.currentUser={};
        vm.contracteNoi = [];
        vm.titlu='';
        vm.dataLoading = true;
        vm.header = {col1:'', col2:'', col3:'', col4:''};
        vm.table=[];
        vm.loadPersonal = loadPersonal;
        vm.loadContracteNoi = loadContracteNoi;
        vm.loadAngajatiPlecati = loadAngajatiPlecati;
        vm.newUsers = newUsers;
        vm.counts={rep1:'', rep2:'', rep3:'', rep4:''};


        (function initController() {
            $http.get('/contract/loadHome').then(
                function (response) {
                    vm.counts = response.data;
                },
                function (error) {
                });

            loadPersonal();
            $timeout(clearLoading, 2000);

        })();


        function loadContracteNoi(){
            vm.dataLoading = true;
            vm.table=[];

            vm.titlu='Contracte Noi';
            vm.header.col1 = 'Nume si Prenume';
            vm.header.col2 = 'Functie';
            vm.header.col3 = 'Tip Contract';
            vm.header.col4 = 'Data Inceput';

            $http.get('/contract/contracteNoi').then(
                function (response) {
                    vm.table = response.data;
                },
                function (error) {
                    FlashService.Error('Eroare la incarcarea listei angajatilor!')
                });



            $timeout(clearLoading, 2000);

        }

        function loadAngajatiPlecati(){
            vm.dataLoading = true;
            vm.table=[];

            vm.titlu='Angajati in preaviz';
            vm.header.col1 = 'Nume si Prenume';
            vm.header.col2 = 'Departament';
            vm.header.col3 = 'Functie';
            vm.header.col4 = 'Data Incheiere Contract';

            $http.get('/contract/leavingAngajati').then(
                function (response) {
                    vm.table = response.data;
                },
                function (error) {
                    FlashService.Error('Eroare la incarcarea listei angajatilor!')
                });

            $timeout(clearLoading, 2000);
        }

        function newUsers(){
            vm.dataLoading = true;
            vm.table=[];
            vm.titlu='Utilizatori de aprobat';
            vm.header.col1 = 'Nume si Prenume';
            vm.header.col2 = 'Username';
            vm.header.col3 = 'Departament';
            vm.header.col4 = 'Data creari';

            $http.get('/contract/newUsers').then(
                function (response) {
                    vm.table = response.data;
                },
                function (error) {
                    FlashService.Error('Eroare la incarcarea listei angajatilor!')
                });

            $timeout(clearLoading, 2000);
        }

        function loadPersonal(){
            vm.dataLoading = true;
            vm.table=[];
            vm.titlu='Taskuri';
            vm.header.col1 = 'Nume si Prenume';
            vm.header.col2 = 'Departament';
            vm.header.col3 = 'Functie';
            vm.header.col4 = 'Data Inceput';
            $http.get('/contract/personalActiv').then(
                function (response) {
                    vm.table = response.data;
                },
                function (error) {
                    FlashService.Error('Eroare la incarcarea listei angajatilor!')
                });


            $timeout(clearLoading, 2000);
        }

        function clearLoading(){
            vm.dataLoading = false;
        }



    }

})();