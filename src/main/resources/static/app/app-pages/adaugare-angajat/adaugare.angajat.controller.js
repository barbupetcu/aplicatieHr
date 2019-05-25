(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdaugareAngajat', AdaugareAngajat);

    AdaugareAngajat.$inject = ['UserService','FlashService'];
    function AdaugareAngajat(UserService, FlashService) {
        var vm = this;
        vm.today = new Date();
        vm.maxBirthday = new Date(
            vm.today.getFullYear()-16, vm.today.getMonth(), vm.today.getDate()
        );
        vm.age='';
        vm.judete=[];
        vm.orase=[];
        vm.oraseAdresa=[];

        (function initController() {
            UserService.loadJudete()
                .then(function (response){
                    if(response.success){
                        vm.judete=response.data;
                    } else {
                        FlashService.Error (response.message);
                    }

                });
        })();

        vm.calculateAge = function calculateAge(d1){
            var months;
            months = (vm.today.getFullYear() - d1.getFullYear()) * 12;
            months -= d1.getMonth();
            months += vm.today.getMonth();
            var result='';
            var years = Math.floor(months/12);
            var monthsRest = months % 12;
            if (years>0){
                result += years + ' ani ';
            }
            if (monthsRest>1){
                result += monthsRest + ' luni';
            }
            else if(monthsRest>0){
                result += monthsRest + ' luna';
            }
            vm.age = result;
        }

        vm.updateCity = function updateCity(judet){
            UserService.loadOrase(judet)
                .then(function (response){
                    if(response.success){
                        vm.orase= response.data;
                    } else {
                        FlashService.Error (response.message);
                    }

                });
        }

        vm.updateCityAdresa = function updateCityAdresa(judet){
            UserService.loadOrase(judet)
                .then(function (response){
                    if(response.success){
                        vm.oraseAdresa= response.data;
                    } else {
                        FlashService.Error (response.message);
                    }
                });
        }

        vm.adaugareAngajat = function adaugareAngajat(){
            UserService.adaugaAngajat(vm.angajat.dicPerso)
                .then(function (response){
                    if(response.success){
                        FlashService.Success (response.data);
                    } else {
                        FlashService.Error (response.message);
                    }
                });
        }




    }

})();