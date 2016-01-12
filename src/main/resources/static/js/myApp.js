(function(){
    var app = angular.module('myApp', []);

    app.controller('KontosCtrl',function($scope,$http){
        $http.get("/transactions").then(function (response) {
            console.log( "GET Response : " + JSON.stringify(response));
            console.log( "GET Data : " + JSON.stringify(response.data));
            $scope.transactions = response.data;
        });

        $scope.ueberweise = function(){
            var dataObj = { betrag : $scope.betrag, _csfr: $scope._csfr};

            var response = $http.post('/transactions',dataObj);
            response.success(function(data,status,headers,config){
                $scope.message = data;
                console.log( "POST Response : " + JSON.stringify(response));
                console.log( "POST Data : " + JSON.stringify(response.$$state.value.data));
                $scope.transactions.push(response.$$state.value.data);
            });
            response.error(function(data,status,headers,config){
                console.log( "failure message: " + JSON.stringify({data:data}));
            })
            $scope.betrag='';
        }
    });
})();