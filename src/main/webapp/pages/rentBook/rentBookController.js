var app = angular.module("myApp");

app.controller('rentBookController', ['$scope', '$state', '$rootScope', '$cookieStore', '$location', 'BookService', '$stateParams', function ($scope, $state, $rootScope, $cookieStore, $location, BookService, $stateParams) {

    $scope.bookExists = false;

    function getBook() {
        BookService.getBook($stateParams.bookId).success(function (data) {
            $scope.book = data;
            console.log($scope.book);
            if ($scope.book !== null) {
                $scope.bookExists = true;
            }
        });
    }

    getBook();

    $scope.libraryName = $rootScope.libraryName;
    $scope.libraryAddress = $rootScope.libraryAddress;
    $scope.username = $rootScope.profile.iss;
    $scope.email = $rootScope.profile.email;
    $scope.address = $rootScope.profile.address;
    $scope.addressExists = angular.isDefined($scope.address);

    console.log($rootScope.profile);

    $scope.rentToHome = function(){

    };

    $scope.rentFromLibrary = function(){
        var formData = new FormData();
        formData.append('book', $scope.book.id);
        formData.append('user', $rootScope.profile.iss);
        BookService.rentBook(formData).success(function(data){
          $scope.alertLibraryMessage = data;
        })
    };

}]);