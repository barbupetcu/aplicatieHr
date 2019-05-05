(function () {
    'use strict';

    angular
        .module('app')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['UserService', '$rootScope', 'FlashService','$uibModal', 'DataService'];
    function TaskController(UserService, $rootScope, FlashService, $uibModal, DataService) {
        var vm = this;
        vm.activeTask = '';
        vm.tasksData=[];

        (function initController() {
            loadTasks();
        })();

        function loadTasks(){
            UserService.getTasksByDept().then(function(response){
                vm.tasksData = response.data;
                vm.activeTask=vm.tasksData[0].id
            });
        }
        
        vm.selectTask = function(value){
            vm.activeTask = value;
            loadComments(value);
        };

        vm.isActive = function(value){
            if(vm.activeTask == value){
                return true;
            }
            else {
                return false;
            }
        };

        vm.saveTask = function(isEdit){
            if (isEdit){
                DataService.setActiveTask(vm.activeTask);
            }
            $uibModal.open({
                templateUrl: 'app/app-modal/task.adaugare.html',
                controller: 'AddTaskController',
                controllerAs: 'vm'
            }).result.then(function(){
                loadTasks();
            }, function(res){})
        };

        vm.sendComment = function(){
            vm.newComment.task={};
            vm.newComment.task.id = vm.activeTask;
            vm.newComment.user={};
            vm.newComment.user.id = $rootScope.globals.currentUser.id;

            UserService.sendComment(vm.newComment).then(function(response){
                loadComments(vm.activeTask);
                vm.newComment={};
            });            
        }

        function loadComments(idTask){
            UserService.loadCommentsByTask(idTask).then(function(response){
                vm.activeComments = response;
            }); 
        }
    }

})();