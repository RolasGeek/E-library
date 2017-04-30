var app = angular.module("myApp");

app.controller('profileController', ['$scope', '$rootScope', '$cookieStore', '$location', function($scope, $rootScope, $cookieStore, $location) {
	$scope.username = $rootScope.profile.iss;
	$scope.email = $rootScope.profile.email;
	$scope.editProfile = function(){
		$location.path('/editProfile');
	}
}]);