(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['UserService', '$location', 'FlashService'];
    function RegisterController(UserService, $location, FlashService) {
        var vm = this;
        
        vm.depts = [];

        (function initController() {
            //se incarca departamentele
            loadDepts();
        })();

        function loadDepts(){
            if(vm.depts.length ==0){
                UserService.getDepts()
                    .then(function(response){
                        if (response.success){
                            vm.depts = response.dept;
                        } else {
                            FlashService.Error(response.message);
                        }
                    });
            }
        };
        
        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user, vm.perso)
                .then(function (response) {
                    if (response.success) {
                        FlashService.Success('Ingistrarea a fost efectuata cu succes', true);
                        $location.path('/login');
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading = false;
                    }
                });
        };
    }

})();
