(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeControllerMan', HomeControllerMan);

    HomeControllerMan.$inject = ['DataService', 'UserService', '$rootScope', '$interval'];
    function HomeControllerMan(DataService, UserService, $rootScope,$interval) {
        var vm = this;
        vm.setSelectedId=setSelectedId;

        (function initController() {
            loadDisabledUsers();
            loadHome();
            
        })();

        function loadDisabledUsers() {
            UserService.LoadDisabledUsers($rootScope.globals.currentUser.dept)
            .then(function (response){
                if(response){
                    vm.disabledUsers = response
                }
            });
        };

        function setSelectedId(selectedId) {
            DataService.setSelectedId(selectedId);
        };

        function loadHome(){
            UserService.loadHomeManager($rootScope.globals.currentUser.dept)
            .then(function (response){
                if(response){
                    vm.myTasks = response.myTasks;
                    vm.countTasks = response.countTasks;
                    vm.deptUsers = response.deptUsers;
                }
            });
        };
    }

})();