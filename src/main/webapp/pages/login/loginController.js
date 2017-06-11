var app = angular.module("myApp");

app.controller('loginController', ['$scope','$rootScope', '$cookieStore', 'LoginService', '$location', function($scope, $rootScope,$cookieStore, LoginService, $location) {
	$rootScope.logedIn = false;
	$rootScope.token = '';
	$scope.login = function() {
		var loginData =  {username: $scope.username, password: $scope.password}
			LoginService.login(loginData)
				.success(function(data) {
				if(data == '1' ) {
					$scope.error = "Neteisingas slaptažodis";
				} else if (data == '0') {
					$scope.error = "Klaidingi duomenys";
				} else {
				console.log(data);
				$cookieStore.put('token', data.token);
				$cookieStore.put('userData', data);
				$rootScope.logedIn = true;
				$rootScope.profile =data;
				$location.path('/home');
				console.log("prisijungiau");
				}
			})	
	
		
	};
	$scope.createAccount = function(){
		$location.path('/register');
		console.log("go to createAccount");
	};
	
	//Functions
	function setCredentials(token) {
		var decoded = jwt_decode(token);
		console.log(decoded);
		$rootScope.globals = {
                currentUser: decoded
                }
            };
	
	
	function getCustomers() { 
		LoginService.getUsers()
	.then(function(response) {
		$scope.users = response.data;
	}, function(error) {
		 $scope.status = 'Unable to load customer data: ' + error.message;
	});
}

}]);