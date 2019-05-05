(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['UserService', '$rootScope'];
    function HomeController(UserService, $rootScope) {
        var vm = this;
        vm.currentUser={};
        vm.myTasks={};


        (function initController() {
            UserService.loadHomeData($rootScope.globals.currentUser.id)
            .then(function (response){
                if(response){
                    vm.currentUser = response.currentUser;
                    vm.myTasks = response.myTasks;
                    vm.countTasks = response.countTasks;
                }
            });
        })();
    }

})();