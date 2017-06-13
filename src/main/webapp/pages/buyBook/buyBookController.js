var app = angular.module("myApp");

app.controller('buyBookController', ['$scope', '$state', '$rootScope', '$cookieStore', '$location', 'BookService', '$stateParams', function ($scope, $state, $rootScope, $cookieStore, $location, BookService, $stateParams) {

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

    console.log($rootScope.profile);

    $scope.email = $rootScope.profile.email;

    $scope.buyBook = function(){
        var formData = new FormData();
        formData.append('book', $scope.book.id);
        formData.append('user', $rootScope.profile.username);
        BookService.buyBook(formData).success(function(data){
            $scope.alertMessage = data;
        })
    }
}]);