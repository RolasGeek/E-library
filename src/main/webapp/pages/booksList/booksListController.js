
var app = angular.module("myApp");

app.controller('listController', ['$scope','$state' ,'$rootScope', '$cookieStore', '$location', 'BookService', function($scope, $state, $rootScope, $cookieStore, $location, BookService) {
	$scope.searchData = $state.params.searchQuery;
	$scope.search = function(searchData) {
		console.log(searchData);
		$state.go('bookList', {searchQuery : searchData});
	}
	
	getSearch($scope.searchData);
	
	function getSearch(search) {
		return BookService.getSearch(search).success(function(books){
			$scope.books = books;
			console.log($scope.books);
		});
	}
}]);