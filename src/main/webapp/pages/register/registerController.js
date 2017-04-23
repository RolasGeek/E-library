var app = angular.module("myApp");

app.controller('registerController', ['$scope', '$rootScope', '$cookieStore', '$location', function($scope, $rootScope, $cookieStore, $location) {
	
	$scope.register = function() {
		$location.path('/register');
		console.log("reg done");
	};
}]);