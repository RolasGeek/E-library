var app = angular.module("myApp");

app.controller('openBookController', ['$scope','$state' ,'$rootScope', '$cookieStore', '$location', 'BookService', '$stateParams', function($scope, $state, $rootScope, $cookieStore, $location, BookService, $stateParams) {

    function getBook(){
        BookService.getBook($stateParams.bookId).success(function(data){
            $scope.book = data;
            console.log($scope.book);
        });
    }

    getBook();

}]);