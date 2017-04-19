var app = angular.module("myApp");

app.factory('loginService',['$http', function ($http) {
	
	var loginService = {};
	var urlBase = 'rest/users/';
	
	loginService.login = function(user) {
		return $http.post(urlBase + "login", user)
	}
	
	
	loginService.getUsers =  function() {
		return $http.get(urlBase);
	}
	return loginService;
	
}]);