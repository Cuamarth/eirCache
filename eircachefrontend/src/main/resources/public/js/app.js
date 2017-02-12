var eirAPP =angular.module('eirAPP', [])

    eirAPP.service('eirDataService',function($http) {
        return {
            find: find
        }

        function find (postCode) {
             return $http({
                       method: 'GET',
                       url: '/address/uk/'+postCode,
                       params: 'format=json'
                    });
        }
    });


     eirAPP.controller('eirAPPController', function(eirDataService) {
            var eirCodeForm = this;

            eirCodeForm.results = [];
            eirCodeForm.noResults=false;
            eirCodeForm.noResultsText="";

            eirCodeForm.sendForm=function(){
                eirCodeForm.noResults=false;
                eirDataService.find(eirCodeForm.searchText).then(function(data) {
                     if(data.data.length>0){
                        eirCodeForm.results= data.data;
                     }else{
                        eirCodeForm.setNoResultsMessage('No results found fot the introduced  code, please try with other one');
                     }
               }).catch(function(data){
                    eirCodeForm.setNoResultsMessage('A temporal server error has ocurred, please try again later');
               });

            }
            eirCodeForm.setNoResultsMessage=function(message){
               eirCodeForm.results = []
               eirCodeForm.noResults=true;
               eirCodeForm.noResultsText=message;
            }

      });