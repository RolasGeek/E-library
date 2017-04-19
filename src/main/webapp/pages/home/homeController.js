var app = angular.module("myApp");

app.controller('homeController', ['$scope', '$rootScope', '$cookieStore', '$location', function($scope, $rootScope, $cookieStore, $location) {
	
	$scope.logOut = function() {
		$rootScope.profile = null;
		$rootScope.logedIn = false;
		$cookieStore.remove('token');
		$cookieStore.remove('userData');
		$location.path('/login');
	};
}]);