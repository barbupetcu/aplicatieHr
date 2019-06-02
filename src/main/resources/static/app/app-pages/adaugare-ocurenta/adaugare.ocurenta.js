(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdaugareOcurenta', AdaugareOcurenta);

    AdaugareOcurenta.$inject = ['UserService', 'FlashService','$uibModalInstance', 'DataService'];
    function AdaugareOcurenta(UserService, FlashService, $uibModalInstance, DataService) {
        var vm = this;
        vm.contractIsto = DataService.getContractIsto();
        var dateEff = new Date(vm.contractIsto.dateEff);
        vm.minDateEff=new Date(
            dateEff.getFullYear(),  dateEff.getMonth(), dateEff.getDate()+1);



        vm.addOcurenta = function(){
            UserService.addOcurenta(vm.contractIsto)
                .then(function(response){
                    if(response.success){
                        FlashService.Success(response.data, true);
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