(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdeverinteController', AdeverinteController);

    AdeverinteController.$inject = ['UserService', '$rootScope','$timeout','$http','FlashService', '$interval', '$q','DataService', '$location'];
    function AdeverinteController(UserService, $rootScope,$timeout,$http,FlashService, $interval, $q, DataService, $location) {
        var vm = this;
        vm.header=[];
        vm.adeverinte=[];

        (function initController() {
            vm.dataLoading = true;

            UserService.loadHeader(true)
                .then(function (response){
                    if(response.success){
                        vm.header=response.data;
                    } else {
                        FlashService.Error (response.message);
                    }

                });

            vm.adeverinte = [{id:'salariu', name: 'Adeverinta de salariat'},{id: 'angajat', name: 'Adeverinta de angajat'} ]


            $timeout(clearLoading, 2000);

        })();

        function clearLoading(){
            vm.dataLoading = false;
        }

        vm.cancelAdeverinta = function cancelAdeverinta(){
            $location.path('/');
        }

        vm.creazaAdeverinta = function creazaAdeverinta(){
            var deferred = $q.defer();
            var defaultFileName='Adeverinta.docx';

            var tesst  = vm.adev;

            $http.post('/contract/eliberareAdeverinta/', JSON.stringify(vm.adev), {responseType: "arraybuffer" }).then(
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
                }, function (data, status) {
                    FlashService.Error('Eroare export adeverinta', true);
                    var e = /* error */
                        deferred.reject(e);
                });
            return deferred.promise;

        }




    }

})();