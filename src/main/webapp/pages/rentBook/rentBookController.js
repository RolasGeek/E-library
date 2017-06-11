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
    $scope.username = $rootScope.profile.username;
    $scope.email = $rootScope.profile.email;
    $scope.address = $rootScope.profile.address;
    $scope.addressExists = (angular.isDefined($scope.address) && $scope.address !== "");

    console.log($rootScope.profile);

    function rent(toHome){
        var formData = new FormData();
        formData.append('book', $scope.book.id);
        formData.append('user', $scope.username);
        formData.append('toHome', toHome);
        BookService.rentBook(formData).success(function(data){
            if (toHome){
                $scope.alertHomeMessage = data;
            }
            else{
                $scope.alertLibraryMessage = data;
            }
        })
    }

    $scope.rentToHome = function(){
        rent(true);
    };

    $scope.rentFromLibrary = function(){
        rent(false);
    };

}]);