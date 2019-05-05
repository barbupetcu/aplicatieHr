(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddTaskController', AddTaskController);

    AddTaskController.$inject = ['UserService', '$rootScope', 'FlashService','$uibModalInstance', 'DataService'];
    function AddTaskController(UserService, $rootScope, FlashService, $uibModalInstance, DataService) {
        var vm = this;
        vm.activeTask = DataService.getActiveTask();
        vm.newTask={};

        (function initController() {
            //inciarca Iteratii
            UserService.getIteration()
                .then(function(response){
                    vm.interation = response;
            });
            //incarca lista de prioritati
            UserService.getPriority()
                .then(function(response){
                    vm.priority = response;
            });

            //incarca lista de dificultati
            UserService.getDifficulty()
                .then(function(response){
                    vm.difficulty = response;
            });


            //incarca lista de useri disponibili
            UserService.getTeamUsers($rootScope.globals.currentUser.dept)
                .then(function(response){
                    vm.teamUsers = response;
            });

            //incarca detaliile task-ului in caz de editare
            if (vm.activeTask){
                UserService.getTask(vm.activeTask)
                .then(function(response){
                    vm.newTask = response;
                    DataService.setActiveTask(null);
            });
            }


        })();
        

        vm.AddTask = function(){
            vm.dataLoading=true;
            UserService.addTask(vm.newTask)
                    .then(function(response){
                        if(response.success){
                            if(vm.activeTask){
                                FlashService.Success('Task-ul a fost salvat cu succes', true);
                            }else{
                                FlashService.Success('Task-ul a fost adaugat cu succes', true);
                            }
                            
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