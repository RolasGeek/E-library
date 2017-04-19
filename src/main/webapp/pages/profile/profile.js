var app = angular.module("myApp");

app.controller('profileController', ['$scope', '$rootScope', '$cookieStore', function($scope, $rootScope, $cookieStore) {
	console.log("yourmom");
	if($rootScope.logedIn) {
	$scope.username = $rootScope.profile.iss;
	$scope.email = $rootScope.profile.email;
	if($rootScope.logedIn) {
		console.log("gyvas");
	}
	}
}]);