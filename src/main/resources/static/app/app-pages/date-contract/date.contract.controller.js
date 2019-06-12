(function () {
    'use strict';

    angular
        .module('app')
        .controller('DateContract', DateContract);

    DateContract.$inject = ['UserService','FlashService','DataService','$uibModal'];
    function DateContract(UserService, FlashService, DataService, $uibModal) {
        var vm = this;
        vm.header=[];
        vm.vechime='';
        vm.today = new Date();
        vm.departamente = [];
        vm.posturi = [];
        vm.ocurente = [];
        vm.tipuriContract = [{id:1,name:'Contract Perioada Determinata'},
                {id:2, name:'Contract Perioada Nedeterminata'},
                {id:3, name:'Intermediar'},
                {id:4, name:'Stagiar'}];

        vm.oreContract = [{id:1,name:'4 ore'},
            {id:2, name:'6 ore'},
            {id:3, name:'8 ore'}];

        vm.loadContract = loadContract;


        (function initController() {
            vm.dataLoading=true;
            UserService.loadDepts()
                .then(function (response){
                    if(response.success){
                        vm.departamente= response.data;
                    } else {
                        FlashService.Error (response.message);
                    }

                });
            loadHeader();

            if (DataService.getUsername()){
                loadContract(DataService.getUsername());
            }
            
            
            vm.dataLoading=false;
            vm.showContractInactiv = false;



        })();

        function loadHeader(){
            UserService.loadHeader()
                .then(function (response){
                    if(response.success){
                        vm.header=response.data;
                        vm.persoanaHeader = arrayObject(vm.header, DataService.getUsername(),"marca");
                        DataService.setUsername(null);
                    } else {
                        FlashService.Error (response.message);
                    }

                });
        }

        function arrayObject(myArray, searchTerm, property) {
            for(var i = 0;  i < myArray.length; i++) {
                if (myArray[i][property] === searchTerm) return myArray[i];
            }
            return null;
        };

        vm.calculateAge = function calculateAge(d1){
            var months;
            d1 = new Date(d1);
            var today;
            if (vm.contractIsto.contract.endDate == null){
                today = vm.today;
            }
            else{
                today = new Date(vm.contractIsto.contract.endDate);
            }
            months = (today.getFullYear() - d1.getFullYear()) * 12;
            months -= d1.getMonth();
            months += today.getMonth();
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
            vm.vechime = result;
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

        function loadContract(marca){
            vm.dataLoading=true;
            UserService.loadContract(marca)
                .then(function (response){
                    if(response.success){
                        vm.updatePosts(response.data.contractIsto.dept.deptId);
                        vm.contractIsto=response.data.contractIsto;
                        vm.ocurente = response.data.ocurente;
                        vm.calculateAge(response.data.contractIsto.contract.startDate);
                        vm.ocurenta = response.data.contractIsto.dateEff;
                        // vm.maxPerProba = vm.contractIsto.contract.startDate;
                        vm.dataLoading=false;

                    } else {
                        FlashService.Error (response.message);
                        vm.contractIsto=null;
                        vm.vechime='';
                        vm.dataLoading=false;
                    }
                });
        }

        vm.loadOcurenta = function loadOcurenta(dateEff, marca){
            vm.dataLoading=true;
            UserService.loadOcurenta(dateEff, marca)
                .then(function (response){
                    if(response.success){
                        vm.updatePosts(response.data.dept.deptId);
                        vm.contractIsto=response.data;
                        vm.calculateAge(response.data.contract.startDate);
                        vm.dataLoading=false;
                    } else {
                        FlashService.Error (response.message);
                        vm.contractIsto=null;
                        vm.vechime='';
                        vm.dataLoading=false;
                    }
                });
        }

        vm.showStatus = function showStatus(activ){
            vm.showContractInactiv = !activ;
        }

        vm.checkNullDate = function checkNullDate(data){
            if (data == null){
                return 'Prezent';
            }
            else{
                var endDate = new Date(data);
                var test = endDate.getMonth();
                var month = '';
                var day = '';
                if (endDate.getMonth()<9){

                    month = '0' + (endDate.getMonth() +1);
                }
                else{
                    month = endDate.getMonth()+1;
                }

                if (endDate.getDate()<10){
                    day = '0'+endDate.getDate();
                }
                else{
                    day = endDate.getDate();
                }

                var today=new Date();
                return day+'/'+month+'/'+today.getFullYear();
            }
        }

        vm.addOcurenta = function(){
            DataService.setContractIsto(vm.contractIsto);
            $uibModal.open({
                templateUrl: 'app/app-pages/adaugare-ocurenta/adaugare.ocurenta.html',
                controller: 'AdaugareOcurenta',
                controllerAs: 'vm'
            }).result.then(function(){
                vm.loadContract(vm.persoanaHeader.marca);

            }, function(){})
        };




    }

})();