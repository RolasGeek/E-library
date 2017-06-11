

var app = angular.module("myApp");

app.factory('BookService',['$http', function ($http) {
	var booksService = {};
	var urlBase = 'rest/books/';
	
	booksService.insert = function(book) {
		return $http.post(urlBase + "insert", book, {
			 transformRequest: angular.identity,
			 headers: {'Content-Type': undefined}
		});
	};

    booksService.rentBook = function(data){
        return $http.post(urlBase + "rentBook", data, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
    };
	
	booksService.get = function(bookId) {
		return $http.get(urlBase+ "get/" + bookId);
	};
	
	booksService.removeBook = function(bookId) {
		return $http.get(urlBase+ "delete/" + bookId);
	};

	booksService.getBook = function(bookId){
		return $http.get(urlBase + "getBook/" + bookId);
	};

	booksService.getRents = function(username){
		return $http.get(urlBase + "getRents/" + username);
	};

	booksService.getAll = function() {
		return $http.get(urlBase+ "getAll");
	};
	
	booksService.getSearch = function(search) {
		return $http.get(urlBase+"getSearch/"+search );
	};
	
	return booksService;
}]);