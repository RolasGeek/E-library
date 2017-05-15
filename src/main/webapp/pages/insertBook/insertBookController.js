var app = angular.module("myApp")

app.controller("insertBookController", ['$scope', '$rootScope', '$cookieStore', 'BookService' , '$location', function($scope, $rootScope, $cookieStore, BookService ,$location) {
	
	$scope.insert = function(book) {
		BookService.insert(book).success(function(data) {
			$scope.render=true;
			$scope.alertclass="alert alert-success";
			$scope.alertmessage="Book has been inserted";
		})
	}
}]);