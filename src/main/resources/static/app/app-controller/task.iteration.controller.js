(function () {
    'use strict';

    angular
        .module('app')
        .controller('IterationController', IterationController);

    IterationController.$inject = ['UserService', '_', 'FlashService', '$mdDialog', '$rootScope','$uibModal'];
    function IterationController(UserService, _, FlashService, $mdDialog, $rootScope, $uibModal) {

        var vm=this;
        //lista cu toate task-urile
        vm.tasksData = [];
        
        //listele de selectie
        vm.selectedA = [];
        vm.selectedB = [];
        vm.selectedC = [];
        //listele de task-uri divizate dupa status
        vm.listA = [];
        vm.listB = [];
        vm.listC = [];
        vm.dataModif = true;
        vm.dataChecked = true;
        vm.taskUsers = [];
        vm.iterations = [];

        vm.reloadtasks = function(){
            UserService.getTasks(vm.selectedIteration.id).then(function(response){
                vm.tasksData = response.data;
                splitTasks();
            });
        };
        
       

        (function initController() {
            
            
            UserService.getTeamUsers($rootScope.globals.currentUser.dept)
                .then(function(response){
                    vm.taskUsers = response;
            });

            UserService.getIteration()
                .then(function(response){
                    vm.iterations = response;
                    vm.selectedIteration = vm.iterations[vm.iterations.length -1];
                    vm.reloadtasks();
            });

            

            

        })();

        
        

        function showAlert(ev) {
            $mdDialog.show(
              $mdDialog.alert()
                .title('Asigurati-va ca toate task-urile selectate sunt asignate.')
                .textContent('Task-urile fara proprietar nu pot fi In Progress.')
                .ariaLabel('Eroare')
                .ok('Am inteles')
                .targetEvent(ev)
            );
          
        };
        

        function splitTasks(){
            vm.listA = vm.tasksData.filter(function (item){
                if (item.status === null){
                    return item.status === null;
                } else{
                    return item.status.id === 2;
                }
                
            });
            vm.listB = vm.tasksData.filter(function (item){
                return item.status.id === 3;
            });
            vm.listC = vm.tasksData.filter(function (item){
                return item.status.id === 4;
            });
        };

        
        
        function arrayObjectIndexOf(myArray, searchTerm, property) {
            for(var i = 0;  i < myArray.length; i++) {
                if (myArray[i][property] === searchTerm) return i;
            }
            return -1;
        };
        
        vm.aToB = function(ev) {
            //verificam daca in lista exista task-uri fara user
            for (var i = 0; i < vm.selectedA.length; i++) {
                var moveId = arrayObjectIndexOf(vm.tasksData, vm.selectedA[i], "id");
                if (vm.tasksData[moveId].user === null){
                    reset();
                    showAlert(ev);
                    {break;}


                }
            }

            for (var i = 0; i < vm.selectedA.length; i++) {
                var moveId = arrayObjectIndexOf(vm.tasksData, vm.selectedA[i], "id");
                vm.tasksData[moveId].status.id = 3;

                //marcam task-ul ca fiind modificat
                vm.tasksData[moveId].modified = new Date(); 

                vm.listB.push(vm.tasksData[moveId]);
                var delId = arrayObjectIndexOf(vm.listA, vm.selectedA[i], "id"); 
                vm.listA.splice(delId,1);
            }
            reset();
            vm.dataModif=false;
            vm.listModif=true;
        };
        
        vm.bToA = function() {
          for (var i = 0; i < vm.selectedB.length; i++) {
            var moveId = arrayObjectIndexOf(vm.tasksData, vm.selectedB[i], "id");
            vm.tasksData[moveId].status.id = 2;

            //marcam task-ul ca fiind modificat
            vm.tasksData[moveId].modified = new Date(); 

            vm.listA.push(vm.tasksData[moveId]);
            var delId = arrayObjectIndexOf(vm.listB, vm.selectedB[i], "id"); 
            vm.listB.splice(delId,1);
          }
          reset();
          vm.dataModif=false;
          vm.listModif=true;
        };

        vm.bToC = function() {
            for (var i = 0; i < vm.selectedB.length; i++) {
              var moveId = arrayObjectIndexOf(vm.tasksData, vm.selectedB[i], "id");
              //actualizam statusul
              vm.tasksData[moveId].status.id = 4;

              //marcam task-ul ca fiind modificat
              vm.tasksData[moveId].modified = new Date(); 

              vm.listC.push(vm.tasksData[moveId]);
              var delId = arrayObjectIndexOf(vm.listB, vm.selectedB[i], "id"); 
              vm.listB.splice(delId,1);
            }
            reset();
            vm.dataModif=false;
            vm.listModif=true;
        };

        vm.cToB = function () {
            for (var i = 0; i < vm.selectedC.length; i++) {
                var moveId = arrayObjectIndexOf(vm.tasksData, vm.selectedC[i], "id"); 
                vm.tasksData[moveId].status.id = 3;

                //marcam task-ul ca fiind modificat
                vm.tasksData[moveId].modified = new Date(); 

                vm.listB.push(vm.tasksData[moveId]);
                var delId = arrayObjectIndexOf(vm.listC, vm.selectedC[i], "id"); 
                vm.listC.splice(delId,1);
            }
            reset();
            vm.dataModif=false;
            vm.listModif=true;
        };
        
        function reset(){
            vm.selectedA=[];
            vm.selectedB=[];
            vm.selectedC=[];
        };

        vm.selectAll = function(selected, list, firstChecked){
            for (var i = 0; i < list.length; i++) {
                var id = list[i].id;
                if (!(selected.indexOf(id) > -1)){
                    selected.push(list[i].id);
                }
                
            }
            vm.dataChecked = firstChecked;
        };
        
        vm.toggleSelectionA = function toggleSelectionA(id) {
            vm.selectedC=[];
            vm.selectedB=[];

            var idx = vm.selectedA.indexOf(id);
        
            // Este deja selectat
            if (idx > -1) {
                vm.selectedA.splice(idx, 1);
            }
        
            // este nou selectat
            else {
                vm.selectedA.push(id);
            }

            if(vm.selectedA.length>0){
                vm.dataChecked = false;
            } else {
                vm.dataChecked = true;
            }
        };

        vm.toggleSelectionB = function toggleSelectionB(id) {
            vm.selectedC=[];
            vm.selectedA=[];
            var idx = vm.selectedB.indexOf(id);
        
            // Este deja selectat
            if (idx > -1) {
                vm.selectedB.splice(idx, 1);
            }
        
            // este nou selectat
            else {
                vm.selectedB.push(id);
            }
        };

        vm.toggleSelectionC = function toggleSelectionC(id) {
            vm.selectedA=[];
            vm.selectedB=[];

            var idx = vm.selectedC.indexOf(id);
        
            // Este deja selectat
            if (idx > -1) {
                vm.selectedC.splice(idx, 1);
            }
        
            // este nou selectat
            else {
                vm.selectedC.push(id);
            }
        };

        vm.sendStatus = function sendStatus(){
            //marcare inceput operatiune de trimite a datelor catre server
            vm.dataLoading=true;
            
            //filtram lista de task-uri pentru a obtine doar task-urile modificates
            var taskModif = vm.tasksData.filter(function (item){
                return item.modified != undefined;
            });

            UserService.sendTasks(taskModif).then(function(response){
                if(response.success){
                    //inlocuim in lista locala task-urile modificate recuperate de la server
                    for (var i = 0; i < response.data.length; i++){
                        //cautam in lista locala elementul din response dupa id
                        var moveId = arrayObjectIndexOf(vm.tasksData, response.data[i].id, "id");
                        //stergem din lista elementul gasit
                        vm.tasksData.splice(moveId,1);
                        //adaugam in lista elementul din response
                        vm.tasksData.push(response.data[i]);
                    }                    
                    splitTasks();
                    vm.dataLoading=false;
                    vm.dataModif=true;

                } else {
                    FlashService.Error(response.message);
                    vm.dataLoading=false;
                }
            });
        };

        vm.changeUser = function changeUser(user, ev){
            vm.dataLoading=true;
            
            var listaTakuri = vm.tasksData;
            for (var i = 0; i < vm.selectedA.length; i++) {
                var moveId = arrayObjectIndexOf(listaTakuri, vm.selectedA[i], "id");
                //golim obiectul user din task
                listaTakuri[moveId].user = {};

                listaTakuri[moveId].user.id = user.id;
  
                //marcam task-ul ca fiind modificat
                listaTakuri[moveId].modified = new Date(); 
            }

            var taskModif = listaTakuri.filter(function (item){
                return item.modified != undefined;
            });

            showConfirm(user, taskModif, ev);

            vm.dataChecked=true;
        }
        
        function showConfirm (user, taskModif, ev) {
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                  .title('Sunteti sigur ca vreti sa schimbati proprietarul pentru task-urile selectate?')
                  .textContent('Toate task-urile selectate vor fi asignate la '+user.name +' ' +user.lastName)
                  .ariaLabel('Schimbare user')
                  .targetEvent(ev)
                  .ok('De acord')
                  .cancel('M-am razgandit');
        
            $mdDialog.show(confirm).then(function() {
                UserService.sendTasks(taskModif).then(function(response){
                    if(response.success){
                        vm.reloadtasks();            
                        vm.dataLoading=false;

    
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading=false;
                    }
                    reset();
                });
              
                
            }, function() {
                vm.reloadtasks();
                vm.dataLoading=false;
                reset();
            });
          };

          vm.addIteration = function(){
            $uibModal.open({
                templateUrl: 'app/app-modal/iteration.adaugare.html',
                controller: 'AddIteration',
                controllerAs: 'vm'
            }).result.then(function(){
                UserService.getIteration()
                .then(function(response){
                    vm.iterations = response;
            });

            }, function(){})
        };

    }
})();
