

var app = angular.module("myApp");

app.factory('BookService',['$http', function ($http) {
	var booksService = {};
	var urlBase = 'rest/books/';
	
	booksService.insert = function(book) {
		return $http.post(urlBase + "insert", book);
	}
	
	
	return booksService;
}]);