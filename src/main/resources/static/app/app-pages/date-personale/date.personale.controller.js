(function () {
    'use strict';

    angular
        .module('app')
        .controller('DatePersonale', DatePersonale);

    DatePersonale.$inject = ['UserService','FlashService'];
    function DatePersonale(UserService, FlashService) {
        var vm = this;
        vm.header=[];
        vm.age='';
        vm.judete=[];
        vm.orase=[];
        vm.oraseAdresa=[];
        vm.today = new Date();
        vm.maxBirthday = new Date(
            vm.today.getFullYear()-16, vm.today.getMonth(), vm.today.getDate()
        );

        (function initController() {
            vm.dataLoading=true;
            UserService.loadJudete()
                .then(function (response){
                    if(response.success){
                        vm.judete=response.data;
                    } else {
                        FlashService.Error (response.message);
                    }

                });
            UserService.loadHeader()
                .then(function (response){
                    if(response.success){
                        vm.header=response.data;
                    } else {
                        FlashService.Error (response.message);
                    }

                });
            vm.dataLoading=false;



        })();

        vm.calculateAge = function calculateAge(d1){
            var months;
            d1 = new Date(d1);
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

        vm.updatePerson = function updatePerson(){
            vm.dataLoading=true;
            if (vm.persoana.marca){
                UserService.updatePerson(vm.persoana)
                    .then(function (response){
                        if(response.success){
                            FlashService.Success (response.data);
                            vm.dataLoading=false;
                        } else {
                            FlashService.Error (response.message);
                            vm.dataLoading=false;
                        }
                    });
            }
            else{
                FlashService.Error ('Selectati o persoana!');
            }

        }

        vm.loadPerson = function loadPerson(marca){
            vm.dataLoading=true;
            UserService.loadPerson(marca)
                .then(function (response){
                    if(response.success){
                        vm.updateCity(response.data.judetulNasterii);
                        vm.updateCityAdresa(response.data.address.countyId);
                        vm.persoana=response.data;
                        vm.calculateAge(vm.persoana.dataNasterii)
                        vm.dataLoading=false;
                    } else {
                        FlashService.Error (response.message);
                        vm.dataLoading=false;
                    }
                });
        }


    }

})();