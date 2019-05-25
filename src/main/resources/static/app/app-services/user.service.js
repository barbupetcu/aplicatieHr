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


        // service.Create = Create;
        // service.LoadDisabledUsers = LoadDisabledUsers;
        // service.DeleteUser = DeleteUser;
        // service.getPriority = getPriority;
        // service.getTeamUsers = getTeamUsers;
        // service.addTask = addTask;
        // service.getTask = getTask;
        // service.getTasks = getTasks;
        // service.sendTasks = sendTasks;
        // service.getDifficulty=getDifficulty;
        // service.getIteration=getIteration;
        // service.getTasksByDept=getTasksByDept;
        // service.sendComment=sendComment;
        // service.loadCommentsByTask=loadCommentsByTask;

        // service.addIteration=addIteration;
        // service.loadHomeManager=loadHomeManager;
        
        
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
            // return $http({
            //     url: '/api/loadHome',
            //     method: "GET",
            //     params: {id: id}
            // }).then(handleSuccess, handleError());
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

        // function loadHomeManager (deptId){
        //     return $http({
        //         url: '/api/loadHomeManager',
        //         method: "GET",
        //         params: {id: deptId}
        //     }).then(handleSuccess, handleError());
        // }
        //
        // function addIteration(iter) {
        //     return $http.put('/api/senditeration', JSON.stringify(iter)).then(handleSuccessWithFlag, handleError('Eroare la crearea iteratiei'));
        // }
        //
        //
        // function sendComment(comment) {
        // 	return $http.post('/api/addcomment', JSON.stringify(comment)).then(handleSuccess, handleError());
        // }
        //
        // function loadCommentsByTask(taskId) {
        //     return $http({
        //         url: '/api/commentsbytask',
        //         method: "GET",
        //         params: {taskId: taskId}
        //     }).then(handleSuccess, handleError());
        // }
        //
        //
        // function sendTasks(tasks) {
        //     return $http.put('/api/sendtasks', JSON.stringify(tasks)).then(handleSuccessWithFlag, handleError('Eroare la crearea task-ului'));
        // }
        //
        // function getTasks(sprintId) {
        //     return $http({
        //         url: '/api/gettasks',
        //         method: "GET",
        //         params: {sprintId: sprintId}
        //     }).then(handleSuccessWithFlag, handleError('Task-urile nu au putut fi incarcate'));
        // }
        //
        // function getTasksByDept() {
        //     return $http({
        //         url: '/api/gettasksByDept',
        //         method: "GET",
        //     }).then(handleSuccessWithFlag, handleError('Task-urile nu au putut fi incarcate'));
        // }
        //
        // function getTask(taskId) {
        //     return $http({
        //         url: '/api/gettask',
        //         method: "GET",
        //         params: {id: taskId}
        //     }).then(handleSuccess, handleError('Detaliile task-ului nu au putut fi incarcate'));
        // }
        //
        // function addTask(task) {
        // 	return $http.post('/api/addtask', JSON.stringify(task)).then(handleSuccess, handleError('Eroare la crearea task-ului'));
        // }
        //
        // function getPriority() {
        //     return $http({
        //         url: '/api/priority',
        //         method: "GET"
        //     }).then(handleSuccess, handleError('Lista prioritatilor nu poate fi incarcata'));
        // }
        //
        // function getDifficulty() {
        //     return $http({
        //         url: '/api/difficulty',
        //         method: "GET"
        //     }).then(handleSuccess, handleError('Lista dificultatilor nu poate fi incarcata'));
        // }
        //
        // function getIteration() {
        //     return $http({
        //         url: '/api/iteration',
        //         method: "GET"
        //     }).then(handleSuccess, handleError('Lista iteratiilor nu poate fi incarcata'));
        // }
        //
        // function getTeamUsers(dept) {
        //     return $http({
        //         url: '/api/getusers',
        //         method: "GET",
        //         params: {dept: dept}
        //     }).then(handleSuccess, handleError('Utilizatorii nu a putut fi incarcati'));
        // }
        //

        //

        //

        //
        // function LoadDisabledUsers(dept) {
        //     return $http({
        //         url: '/api/disabledUsers',
        //         method:"GET",
        //         params: {dept: dept}
        //     }).then(handleSuccess, handleError('Eroare la incarcarea utilizatorilor neactivati'));
        // }
        //
        // function DeleteUser(id) {
        //     return $http({
        //         url: '/api/deleteUser',
        //         method: "DELETE",
        //         params: {id: id}
        //     })
        //         .then(handleSuccess, handleError('Utilizatorul nu a putut fi sters'));
        // }



        

        function handleSuccess(res) {
            return {success:true, data: res.data};
        }

        // function handleSuccess(res) {
        //     return res.data;
        // }

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
