var app = angular.module("myApp");

app.controller('loginController', ['$scope','$rootScope', '$cookieStore', 'loginService', '$location', function($scope, $rootScope,$cookieStore, loginService, $location) {
	$rootScope.logedIn = false;
	$rootScope.token = '';
	$scope.login = function() {
		var loginData =  {username: $scope.username, password: $scope.password}
			loginService.login(loginData)
				.success(function(data) {
				if(data == '1' ) {
					$scope.error = "Neteisingas slaptažodis";
				} else if (data == '0') {
					$scope.error = "Klaidingi duomenys";
				} else {
				$rootScope.token = data;
				var decoded = jwt_decode($rootScope.token);
				console.log(decoded);
				$cookieStore.put('token', $rootScope.token);
				$cookieStore.put('userData', decoded);
				$rootScope.logedIn = true;
				$rootScope.profile =decoded;
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
	
	function sendData(dataObj) {
		loginService.login(dataObj)
	.success(function(data) {
   $rootScope.token = data;
})

}
	
	function getCustomers() { 
		loginService.getUsers()
	.then(function(response) {
		$scope.users = response.data;
	}, function(error) {
		 $scope.status = 'Unable to load customer data: ' + error.message;
	});
}

}]);