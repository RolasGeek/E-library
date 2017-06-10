var app = angular.module("myApp");

app.controller('editBookController', ['$scope', '$state', '$rootScope', '$cookieStore', '$location', 'BookService', '$stateParams', function ($scope, $state, $rootScope, $cookieStore, $location, BookService, $stateParams) {

    function getBook() {
        BookService.getBook($stateParams.bookId).success(function (data) {
            $scope.book = {data: data};
            console.log($scope.book);
        });
    }

    getBook();

    $scope.editBook = function(book){
        var formData = new FormData();
        formData.append('image', book.file);
        formData.append('file1', book.file1);
        var bookData = book;
        var json_test = JSON.stringify(bookData);
        formData.append('book', json_test);

        BookService.insert(formData).success(function(data) {
            $scope.render=true;
            $scope.alertclass="alert alert-success";
            $scope.alertmessage="Book has been updated";
        })
    }

}]);