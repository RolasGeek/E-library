var app = angular.module("myApp");

app.controller('homeController', ['$scope','$state' ,'$rootScope', '$cookieStore', '$location', 'BookService', function($scope, $state, $rootScope, $cookieStore, $location, BookService) {
	$scope.selected = undefined;
	getBook();
	
	function getBook() {
		return BookService.getAll().success(function(books){
			$scope.books = books;
		});
	}
	
	$scope.search = function(searchData) {
		$state.go('bookList', {searchQuery : searchData});
	}
}]);