var app = angular.module("myApp");

app.controller('registerController', ['$scope', '$rootScope', '$cookieStore', 'registerService' , '$location', function($scope, $rootScope, $cookieStore, registerService ,$location) {
	
	$scope.notValid = true;
	
	$scope.register = function() {
		var registerData =  {username: $scope.username, email: $scope.email, password: $scope.password}
		
		registerService.register(registerData)
			.success(function (data){
				if (data == "userExists"){
					$scope.error = "Username already exists";
				}
				else if (data == "emailExists"){
					$scope.error = "User email already exists";
				}
				else if (data == "failed"){
					$scope.error = "Failed to create new user";
				}
				else if (data == "unexpected"){
					$scope.error = "Unexpected error";
				}
				else if (data == "created"){
					$location.path('/register/done');
					console.log("reg done");
				}
				
			})
	};
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