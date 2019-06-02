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
            }
            
        };
    }
})();