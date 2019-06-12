(function () {
    'use strict';

    angular
        .module('app')
        .factory('DataService', DataService);

    function DataService(_) {
        var data = {
            contractIsto : ''
        };
        var reangajare;
        var username ='';
  
        return {
            getContractIsto: function(){
                return data.contractIsto;
            },
            setContractIsto: function(contractIsto){
                data.contractIsto = contractIsto;
            },

            getReangajare: function(){
                return reangajare.contractIsto;
            },
            setReangajare: function(reangajare){
                data.reangajare = reangajare;
            },

            getUsername: function(){
                return username;
            },
            setUsername: function(user){
                username = user;
            }
            
        };
    }
})();