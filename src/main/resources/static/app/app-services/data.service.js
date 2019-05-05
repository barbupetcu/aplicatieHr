(function () {
    'use strict';

    angular
        .module('app')
        .factory('DataService', DataService);

    function DataService(_) {
        var data = {
            activeTask : ''
        };
  
        return {
    
            getSelectedId: function() {
                return localStorage.getItem('selectedUser');
            },
            setSelectedId: function(id) {
                localStorage.setItem('selectedUser', id);
            },
            getActiveTask: function(){
                return data.activeTask;
            },
            setActiveTask: function(idTask){
                data.activeTask = idTask;
            }
            
        };
    }
})();