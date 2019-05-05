(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddIteration', AddIteration);

    AddIteration.$inject = ['UserService', '$uibModalInstance','FlashService'];
    function AddIteration(UserService, $uibModalInstance, FlashService) {
        var vm = this;
        vm.newIteration ={};       

        vm.addIteration = function(){
            UserService.addIteration(vm.newIteration)
                    .then(function(response){
                        if(response.success){
                            FlashService.Success('Iteratia a fost salvata cu succes', true);                            
                            $uibModalInstance.close();
                        } else {
                            FlashService.Error(response.message);
                            $uibModalInstance.close();
                        }
                    });
        };
        
        vm.Dismiss = function(){
            $uibModalInstance.close();
        }
    }

})();