var app = angular.module("myApp");

app.factory('LoginService',['$http', function ($http) {
	
	var loginService = {};
	var urlBase = 'rest/users/';
	
	loginService.login = function(user) {
		return $http.post(urlBase + "login", user)
	}
	
	
	loginService.getUsers =  function() {
		return $http.get(urlBase);
	}
	
	loginService.register = function(user) {
		return $http.post(urlBase + "register", user)
	}
	return loginService;
	
}]);