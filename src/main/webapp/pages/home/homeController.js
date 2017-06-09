var app = angular.module("myApp");

app.controller('homeController', ['$scope','$state' ,'$rootScope', '$cookieStore', '$location', 'BookService', function($scope, $state, $rootScope, $cookieStore, $location, BookService) {
	
	$scope.logOut = function() {
		$rootScope.profile = null;
		$rootScope.logedIn = false;
		$cookieStore.remove('token');
		$cookieStore.remove('userData');
		$location.path('/login');
	}
	
	$scope.selected = undefined;
	getBook();
	
	function getBook() {
		return BookService.getAll().success(function(books){
			$scope.books = books;
			console.log($scope.books);
		});
	}
	
	$scope.search = function(searchData) {
		console.log(searchData);
		$state.go('bookList', {searchQuery : searchData});
	}
}]);