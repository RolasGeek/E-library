var app = angular.module("myApp");

app.controller('bookRentListController', ['$scope', '$rootScope', '$cookieStore', '$location', 'BookService', 'LoginService', '$stateParams', '$timeout' ,'$state', function($scope, $rootScope, $cookieStore, $location, BookService, LoginService, $stateParams, $timeout, $state) {

    function getUser(){
        LoginService.getUser($stateParams.username).success(function(data){
           $scope.user = data;
           getRents();
        });
    }

    getUser();

    function getRents(){
        BookService.getRents($scope.user.username).success(function(data){
            $scope.rents = data;
        });
    }

    $scope.returnBook = function(rentId){
        BookService.setBookReturned(rentId).success(function(data){
            $state.reload()
        })
    };

    $scope.takeBook = function(rentId){
        BookService.setBookTaken(rentId).success(function(data){
            $state.reload()
        })
    };
}]);