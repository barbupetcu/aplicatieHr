(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdaugareAngajat', AdaugareAngajat);

    AdaugareAngajat.$inject = ['UserService','FlashService','$location'];
    function AdaugareAngajat(UserService, FlashService, $location) {
        var vm = this;
        vm.today = new Date();
        vm.maxPerProba = new Date(vm.today.getFullYear(), vm.today.getMonth() + 3, vm.today.getDate());
        vm.maxBirthday = new Date(
            vm.today.getFullYear()-16, vm.today.getMonth(), vm.today.getDate()
        );
        vm.age='';
        vm.judete=[];
        vm.orase=[];
        vm.oraseAdresa=[];
        vm.posturi = [];
        vm.departamente = [];
        vm.contractIsto = { oraInceput: new Date(
                            vm.today.getFullYear(), vm.today.getMonth(), vm.today.getDate(), 9, 0, 0)};

        (function initController() {
            UserService.loadJudete()
                .then(function (response){
                    if(response.success){
                        vm.judete=response.data;
                    } else {
                        FlashService.Error (response.message);
                    }

                });

            UserService.loadDepts()
                .then(function (response){
                    if(response.success){
                        vm.departamente= response.data;
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


        vm.updatePosts = function updatePosts(dept){
            UserService.loadPosts(dept)
                .then(function (response){
                    if(response.success){
                        vm.posturi= response.data;
                    } else {
                        FlashService.Error (response.message);
                    }
                });
        }



        vm.adaugareAngajat = function adaugareAngajat(){
            UserService.adaugaAngajat(vm.contractIsto)
                .then(function (response){
                    if(response.success){
                        FlashService.Success (response.data);
                        $location.path('/');
                    } else {
                        FlashService.Error (response.message);
                    }
                });
        }

        vm.updatePerProba = function updatePerProba(data){
            vm.maxPerProba = new Date(data.getFullYear(), data.getMonth() + 3, data.getDate());
            vm.contractIsto.perioadaProbaData = vm.maxPerProba;
        }




    }

})();