(function (){
    'use strict';

    angular
        .module('app')
        .controller('AproveUserController', AproveUserController);
    
    AproveUserController.$inject = ['DataService', 'UserService', 'FlashService', '$location', '$http', '$timeout'];

    function AproveUserController(DataService, UserService, FlashService, $location, $http,$timeout) {
        var vm = this;
        vm.user=null;
        vm.approveUser = approveUser;
        vm.deleteUser = deleteUser;

        (function initController() {
            $http.get('/contract/loadApproveUser',{params: {username: DataService.getUsername()} }).then(
                function (response) {
                    vm.user = response.data;
                },
                function (error) {
                    FlashService.Error('Eroare la incarcarea datelor!')
                });
            
        })();


        function approveUser(){
            vm.dataAproveUser = true;
            $http.post('/contract/approveUser',JSON.stringify(vm.user)).then(
                function (response) {
                    FlashService.Success('Utilizatorul a fost aprobat!', true)
                    DataService.setUsername(null);
                    $timeout(clearLoading, 1500);
                    $location.path('/');
                },
                function (error) {
                    DataService.setUsername(null);
                    FlashService.Error('Eroare la salvarea datelor!')
                });
        }

        function clearLoading(){
            vm.dataAproveUser = false;
        }

        function deleteUser(){
            vm.dataAproveUser = true;
            $http.post('/contract/deleteUser',JSON.stringify(vm.user)).then(
                function (response) {
                    FlashService.Success('Utilizatorul a fost sters!', true)
                    DataService.setUsername(null);
                    $timeout(clearLoading, 1500);
                    $location.path('/');
                },
                function (error) {
                    DataService.setUsername(null);
                    FlashService.Error('Eroare la salvarea datelor!')
                });
        }
    
    }

})();