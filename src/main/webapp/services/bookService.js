

var app = angular.module("myApp");

app.factory('BookService',['$http', function ($http) {
	var booksService = {};
	var urlBase = 'rest/books/';
	
	booksService.insert = function(book) {
		return $http.post(urlBase + "insert", book, {
			 transformRequest: angular.identity,
			 headers: {'Content-Type': undefined}
		});
	}
	
	booksService.get = function(bookId) {
		return $http.get(urlBase+ "get/" + bookId);
	}
	booksService.getAll = function() {
		return $http.get(urlBase+ "getAll");
	}
	
	return booksService;
}]);