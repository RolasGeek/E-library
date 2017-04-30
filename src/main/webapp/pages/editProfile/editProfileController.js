var app = angular.module("myApp");

app.controller('editProfileController', ['$scope', '$rootScope', '$cookieStore', '$location', function($scope, $rootScope, $cookieStore, $location) {
	
	console.log("Error");
	
	$scope.email = $rootScope.profile.email;
	
	
	$scope.checkPassword = function(){
		if ($scope.password != $scope.passwordRepeat){
			$scope.noMatch = "Passwords doesn't match!";
			$scope.notValid = true;
		}
		else{
			$scope.noMatch = "";
			$scope.notValid = false;
		}
	}
}]);