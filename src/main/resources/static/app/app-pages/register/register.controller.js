(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['UserService', '$location', 'FlashService', '$timeout'];
    function RegisterController(UserService, $location, FlashService, $timeout) {
        var vm = this;
        vm.registerDTO=[];
        vm.user={};
        vm.user.marca=null;


        vm.register = register;
        vm.loadAngajati = loadAngajati;

        function register(marca) {
            vm.dataLoading = true;
            vm.user.marca = marca;
            UserService.register(vm.user)
                .then(function (response) {
                    if (response.success) {
                        FlashService.Success(response.data, true);
                        $location.path('/login');
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading = false;
                    }
                });
        };

        function loadAngajati(){
            UserService.loadAngajati()
                .then(function (response){
                   if(response.success){
                       vm.registerDTO=response.data;
                   } else {
                       FlashService.Error (response.message);
                   }

                });

        }


    }

})();
