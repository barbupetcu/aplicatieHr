(function () {
    'use strict';

    angular
        .module('app')
        .controller('EditUserController', EditUserController);

    EditUserController.$inject = ['UserService', 'FlashService', '$rootScope', '$location'];
    function EditUserController(UserService, FlashService, $rootScope, $location) {
        var vm = this;
        vm.editUser = editUser;
        vm.changePw = changePw;

        vm.user = null;

        initController();

        function initController() {
            loadCurrentUser();
        }
        
        function loadCurrentUser() {
            UserService.GetUserById($rootScope.globals.currentUser.marca)
            .then(function (response) {
                if (response.success) {
                    vm.user=response.data;
                } else {
                    FlashService.Error(response.message);
                }
            });          
        }

        function editUser(){
            vm.dataLoadingUser=true;
            UserService.EditUser(vm.user)
            .then(function (response) {
                if (response.success) {
                    FlashService.Success(response.data, true);
                    $location.path('/login');
                } else {
                    FlashService.Error(response.message);
                    vm.dataLoadingUser=false;
                }
            });
        }

        function changePw(){
            vm.dataLoadingPw=true;
            UserService.ChangePassword($rootScope.globals.currentUser.marca, vm.changePassword.oldPassword, vm.changePassword.newPassword)
            .then(function (response) {
                if (response.success) {
                    FlashService.Success('Parola a fost inregistrata, va rugam sa va logati din nou', true);
                    $location.path('/login');
                } else {
                    FlashService.Error(response.message);
                    vm.dataLoadingPw=false;
                }
            });
        }

    }

})();