var app = angular.module("myApp");

app.controller('homeController', ['$scope', '$rootScope', '$cookieStore', '$location', 'BookService', function($scope, $rootScope, $cookieStore, $location, BookService) {
	
	$scope.logOut = function() {
		$rootScope.profile = null;
		$rootScope.logedIn = false;
		$cookieStore.remove('token');
		$cookieStore.remove('userData');
		$location.path('/login');
	}
	
	$scope.selected = undefined;
	  $scope.states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Dakota', 'North Carolina', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];
	getBook();
	
	function getBook() {
		return BookService.getAll().success(function(books){
			$scope.books = books;
			console.log($scope.books);
		});
	}
}]);