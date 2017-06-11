var app = angular.module("myApp");

app.controller('logoutController', ['$scope','$state' ,'$rootScope', '$cookieStore', '$location', 'BookService', function($scope, $state, $rootScope, $cookieStore, $location, BookService) {
	$rootScope.profile = null;
	$rootScope.logedIn = false;
	$cookieStore.remove('token');
	$cookieStore.remove('userData');
	$location.path('/login');
}]);