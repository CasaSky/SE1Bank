(function(){
    var app = angular.module('myApp', []);

    app.controller('CustomersCtrl',function($scope,$http){
        $http.get("/customers").then(function (response) {
            console.log( "GET Response : " + JSON.stringify(response));
            console.log( "GET Data : " + JSON.stringify(response.data));
            $scope.customers = response.data;
        });

        $scope.addCustomer = function(){
            var dataObj = { name : $scope.name, _csfr: $scope._csfr};

            var response = $http.post('/customers',dataObj);
            response.success(function(data,status,headers,config){
                $scope.message = data;
                console.log( "POST Response : " + JSON.stringify(response));
                console.log( "POST Data : " + JSON.stringify(response.$$state.value.data));
                $scope.customers.push(response.$$state.value.data);
            });
            response.error(function(data,status,headers,config){
                console.log( "failure message: " + JSON.stringify({data:data}));
            })
            $scope.name='';
        }
    });
})();