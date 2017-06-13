var app = angular.module("myApp");

app.controller('editBookController', ['$scope', '$state', '$rootScope', '$cookieStore', '$location', 'BookService', '$stateParams', function ($scope, $state, $rootScope, $cookieStore, $location, BookService, $stateParams) {

    $scope.genres = ['Fictional','Comedy','Drama','Horror','Non-fiction','Realistic fiction','Romance novel','Satire','Tragedy','Tragicomedy','Fantasy','Mythology'];
    $scope.bookgenres = [{"bookId" : null, "genre" : null}];

    function getBook() {
        BookService.getBook($stateParams.bookId).success(function (data) {
            $scope.book = {data: data};
            console.log($scope.book);
        });
        BookService.getGenres($stateParams.bookId).success(function (data){
            $scope.bookGenres = {data: data};

            angular.forEach($scope.bookGenres.data, function(value, key){
                $scope.bookgenres.push({"bookId" : $scope.bookGenres.data[key].id.bookId, "genre" : $scope.bookGenres.data[key].id.genre});
            });
            $scope.bookgenres.splice(0, 1);
            //$scope.bookgenres.push({"bookId" : null, "genre" : null});
        });

    }

    getBook();

    $scope.editBook = function(book){
        var formData = new FormData();
        formData.append('image', $scope.book.file);
        formData.append('file1', $scope.book.file1);
        var json_test = JSON.stringify(book);
        var genres = angular.toJson($scope.bookgenres);
        console.log($scope.bookgenres);
        console.log(genres);
        formData.append('book', json_test);
        formData.append('genres', genres);

        BookService.insert(formData).success(function (data) {
            $scope.render=true;
            $scope.alertclass="alert alert-success";
            $scope.alertmessage="Book has been updated";
        })
    };

    $scope.addLine = function() {
        $scope.bookgenres.push({"bookId" : null, "genre" : null});
        console.log($scope.bookgenres);
    };

    $scope.removeLine = function(genre){
        var index = $scope.bookgenres.indexOf(genre);
        $scope.bookgenres.splice(index, 1);
    }

}]);