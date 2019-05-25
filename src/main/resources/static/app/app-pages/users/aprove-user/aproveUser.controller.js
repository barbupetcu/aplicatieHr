(function (){
    'use strict';

    angular
        .module('app')
        .controller('AproveUserController', AproveUserController);
    
    AproveUserController.$inject = ['DataService', 'UserService', 'FlashService', '$location'];

    function AproveUserController(DataService, UserService, FlashService, $location) {
        var vm = this;
        vm.user=null;
        vm.approveUser = approveUser;
        vm.deleteUser = deleteUser;

        (function initController() {
            loadUser();
            
        })();

        function loadUser() {
            var param = DataService.getSelectedId();
            UserService.GetUserById(DataService.getSelectedId())
            .then(function (response) {
                if (response) {
                    vm.user=response;
                } else {
                    FlashService.Error(response.message);
                }
            });          
        }

        function approveUser(){
            vm.dataAproveUser = true;
            vm.user.enabled = true;
            UserService.EditUser(vm.user).then(function (response) {
                if (response.success) {
                    FlashService.Success('Utilizatorul ' + vm.user.username + ' a fost aprobat', true);
                    $location.path('/homeManager');
                } else {
                    FlashService.Error(response.message);
                    vm.dataAproveUser=false;
                }
            });
        }

        function deleteUser(){
            vm.dataDeleteUser = true;
            UserService.DeleteUser(vm.user.id).then(function (response) {
                if (response.success) {
                    FlashService.Success('Utilizatorul ' + vm.user.username + ' a fost sters', true);
                    $location.path('/homeManager');
                } else {
                    FlashService.Error(response.message);
                    $location.path('/homeManager');
                }
            });
        }
    
    }

})();