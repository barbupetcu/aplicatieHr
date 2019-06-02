(function () {
    'use strict';

    angular
        .module('app')
        .controller('InchidereContract', InchidereContract);

    InchidereContract.$inject = ['UserService','FlashService','$mdDialog'];
    function InchidereContract(UserService, FlashService, $mdDialog) {
        var vm = this;
        vm.header=[];
        vm.departamente = [];
        vm.posturi = [];
        vm.today = new Date();
        vm.maxEndDate;
        vm.isHeader = false;


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
            UserService.loadHeader(true)
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
            vm.dataLoading=false;
            vm.showContractInactiv = false;



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


        vm.loadContract = function loadContract(marca){
            vm.dataLoading=true;
            UserService.loadContract(marca)
                .then(function (response){
                    if(response.success){
                        vm.updatePosts(response.data.contractIsto.dept.deptId);
                        vm.contractIsto=response.data.contractIsto;
                        var dateEff = new Date(response.data.contractIsto.dateEff)
                        vm.maxEndDate = new Date(
                            dateEff.getFullYear(), dateEff.getMonth(), dateEff.getDate()
                        );
                        // vm.maxPerProba = vm.contractIsto.contract.startDate;
                        vm.dataLoading=false;

                    } else {
                        FlashService.Error (response.message);
                        vm.contractIsto=null;
                        vm.dataLoading=false;
                    }
                });
        }

        function formatDate(data){
            if (data == null){
                return '';
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

        vm.closeContract = function closeContract (ev) {
            vm.dataLoading=true;

            var dataFormatata = new Date(vm.contractIsto.contract.endDate);
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Confirmati')
                .textContent('Sunteti sigur ca doriti sa inchdieti contractul angajatului '
                    +vm.contractIsto.contract.persoana.name +' ' +vm.contractIsto.contract.persoana.lastName+' pe ' + formatDate(dataFormatata) + '?')
                .ariaLabel('Schimbare user')
                .targetEvent(ev)
                .ok('De acord')
                .cancel('M-am razgandit');

            $mdDialog.show(confirm).then(function() {
                UserService.closeContract(vm.contractIsto).then(function(response){
                    if(response.success){
                        FlashService.Success(response.data, true);
                        vm.dataLoading=false;
                        $location.path('/');
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading=false;
                    }
                });


            }, function() {
                vm.dataLoading=false;
            });
        };



    }

})();