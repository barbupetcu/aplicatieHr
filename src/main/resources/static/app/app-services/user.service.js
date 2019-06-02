(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http'];
    function UserService($http) {
        var service = {};

        service.loadAngajati = loadAngajati;
        service.register = register;
        service.GetUserById = GetUserById;
        service.EditUser = EditUser;
        service.ChangePassword = ChangePassword;
        service.loadHomeData=loadHomeData;
        service.loadJudete=loadJudete;
        service.loadOrase=loadOrase;
        service.adaugaAngajat=adaugaAngajat;
        service.loadDepts = loadDepts;
        service.loadPosts = loadPosts;
        service.updatePerson = updatePerson;
        service.loadHeader = loadHeader;
        service.loadPerson = loadPerson;
        service.loadContract = loadContract;
        service.loadOcurenta = loadOcurenta;
        service.addOcurenta = addOcurenta;
        service.closeContract = closeContract;

        
        return service;

        function loadAngajati() {
            return $http({
                url: '/user/loadAngajati',
                method: "GET"
            }).then(handleSuccess, handleError);
        }

        function register(user) {
            return $http.post('/user/register', JSON.stringify(user)).then(handleSuccess, handleError);
        }

        function GetUserById(id) {
            return $http({
                url: '/user/getUser/'+id,
                method: "GET"
            }).then(handleSuccess, handleError);
        }

        function EditUser(username) {
            return $http.put('/user/editUser', JSON.stringify(username)).then(handleSuccess, handleError('Utilizatorul nu a putut fi modifcat'));
        }

        function ChangePassword(id, oldPw, newPw) {
            return $http({
                url: '/user/changepassword',
                method: "PUT",
                params: {id: id,
                    oldPw: oldPw,
                    newPw: newPw}
            }).then(handleSuccess, handleError);
        }

        function loadHomeData (id){

        }

        function loadJudete() {
            return $http.get("/data/judete").then(handleSuccess, handleError);
        }

        function register(user) {
            return $http.post('/user/register', JSON.stringify(user)).then(handleSuccess, handleError);
        }

        function loadOrase(judet) {
            return $http.get('/data/orase',{params: {judet: judet}}).then(handleSuccess, handleError);
        }

        function adaugaAngajat(angajat) {
            return $http.post('/contract/adaugaAngajat',JSON.stringify(angajat)).then(handleSuccess, handleError);
        }

        function loadDepts() {
            return $http.get('/data/depts').then(handleSuccess, handleError);
        }

        function loadPosts(deptId) {
            return $http.get('/data/posturi',{params: {deptId: deptId}}).then(handleSuccess, handleError);
        }

        function updatePerson(pers) {
            return $http.put('/contract/updatePerson',JSON.stringify(pers)).then(handleSuccess, handleError);
        }

        function loadHeader(contracActiv) {
            return $http.get('/contract/loadHeader', {params: {contracActiv: contracActiv}}).then(handleSuccess, handleError);
        }

        function loadPerson(marca) {
            return $http.get('/contract/loadPerson', {params: {marca: marca}}).then(handleSuccess, handleError);
        }

        function loadContract(marca) {
            return $http.get('/contract/loadContract', {params: {marca: marca}}).then(handleSuccess, handleError);
        }

        function loadOcurenta(dateEff, marca) {
            var ocurentaDTO = {marca: marca, dateEff: dateEff};
            var test = JSON.stringify(ocurentaDTO);
            return $http.post('/contract/loadOcurenta', JSON.stringify(ocurentaDTO)).then(handleSuccess, handleError);
        }

        function addOcurenta(contractIsto) {
            return $http.post('/contract/addOcurenta', JSON.stringify(contractIsto)).then(handleSuccess, handleError);
        }

        function closeContract(contractIsto) {
            return $http.post('/contract/closeContract', JSON.stringify(contractIsto)).then(handleSuccess, handleError);
        }







        function handleSuccess(res) {
            return {success:true, data: res.data};
        }

        function handleError(error) {
                var message = null;
                if (error.data){
                    if(error.data.message){
                        message = error.data.message;
                    }
                    else {
                        message = error.data;
                    }
                }
                else{
                    message = 'Eroare conexiune server!';
                }
                return{success: false, message: message}
        }
    }

})();
