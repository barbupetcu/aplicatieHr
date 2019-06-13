(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdaugareAngajat', AdaugareAngajat);

    AdaugareAngajat.$inject = ['UserService','FlashService','$location', 'DataService','$mdDialog','$interval', '$q','$http'];
    function AdaugareAngajat(UserService, FlashService, $location, DataService, $mdDialog,$interval, $q,$http) {
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
        vm.maxStartDate;
        vm.contractIsto = { oraInceput: new Date(
                            vm.today.getFullYear(), vm.today.getMonth(), vm.today.getDate(), 9, 0, 0)};
        vm.reangajare;

        vm.tipuriContract = [{id:1,name:'Contract Perioada Determinata'},
            {id:2, name:'Contract Perioada Nedeterminata'},
            {id:3, name:'Intermediar'},
            {id:4, name:'Stagiar'}];

        vm.oreContract = [{id:1,name:'4 ore'},
            {id:2, name:'6 ore'},
            {id:3, name:'8 ore'}];



        vm.init = function init(ev){

            var confirm = $mdDialog.confirm()
                .title('Alegeti tipul de angajare')
                .textContent('Doriti sa angajati o persoana existenta in baza de date?')
                .ariaLabel('Schimbare user')
                .targetEvent(ev)
                .ok('Da')
                .cancel('Adaug o persoana noua');

            $mdDialog.show(confirm).then(function() {
                vm.reangajare = true;
                UserService.loadHeader(false)
                    .then(function (response){
                        if(response.success){
                            if (response.data.length == 0){
                                vm.isHeader = true;
                            }
                            vm.header=response.data;
                        } else {
                            FlashService.Error (response.message);
                        }

                    });


            }, function() {
                vm.reangajare = false;
            });



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

        }


        vm.calculateAge = function calculateAge(d1){
            var months;
            d1 = new Date(d1)
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
            var deferred = $q.defer();
            var defaultFileName='Contract.docx';

            $http.post('/contract/adaugaAngajat', JSON.stringify(vm.contractIsto),{responseType: "arraybuffer" }).then(
                function (response) {
                    var type = response.headers('Content-Type');
                    var disposition = response.headers('Content-Disposition');
                    if (disposition) {
                        var match = disposition.match(/.*filename=\"?([^;\"]+)\"?.*/);
                        if (match[1])
                            defaultFileName = match[1];
                    }
                    defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
                    var blob = new Blob([response.data], { type: type });
                    saveAs(blob, defaultFileName);
                    deferred.resolve(defaultFileName);
                    FlashService.Success ('Datele au fost salvate', true);
                    $location.path('/');
                }, function (data, status) {
                    FlashService.Error (data);
                    var e = /* error */
                        deferred.reject(e);
                });
            return deferred.promise;


        }

        vm.updatePerProba = function updatePerProba(data){
            vm.maxPerProba = new Date(data.getFullYear(), data.getMonth() + 3, data.getDate());
            vm.contractIsto.perioadaProbaData = vm.maxPerProba;
        }

        vm.loadContract = function loadContract(marca){
            vm.dataLoading=true;
            UserService.loadContract(marca)
                .then(function (response){
                    if(response.success){
                        vm.updatePosts(response.data.contractIsto.dept.deptId);
                        vm.updateCity(response.data.contractIsto.contract.persoana.judetulNasterii);
                        vm.updateCityAdresa(response.data.contractIsto.contract.persoana.address.countyId);
                        vm.contractIsto=response.data.contractIsto;
                        vm.calculateAge(new Date(vm.contractIsto.contract.persoana.dataNasterii));
                        var ora = new Date(vm.contractIsto.oraInceput)
                        vm.contractIsto.oraInceput = new Date(
                            ora.getFullYear(), ora.getMonth(), ora.getDate(), 9, 0, 0);
                        var date = new Date(vm.contractIsto.endDate);
                        vm.contractIsto.contract.startDate = new Date(
                            date.getFullYear(), date.getMonth(), date.getDate()+1
                        )
                        vm.updatePerProba(vm.contractIsto.contract.startDate);
                        vm.minStartDate = vm.contractIsto.contract.startDate;
                        // vm.maxPerProba = vm.contractIsto.contract.startDate;
                        vm.dataLoading=false;

                    } else {
                        FlashService.Error (response.message);
                        vm.contractIsto=null;
                        vm.dataLoading=false;
                    }
                });
        }




    }

})();