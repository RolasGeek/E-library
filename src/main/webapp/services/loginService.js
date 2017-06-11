var app = angular.module("myApp");

app.factory('LoginService',['$http', function ($http) {
	
	var loginService = {};
	var urlBase = 'rest/users/';
	
	loginService.login = function(user) {
		return $http.post(urlBase + "login", user)
	}
	loginService.updateUser =  function(user) {
		return $http.post(urlBase+ "update", user);
	}
	
	loginService.getAll =  function() {
		return $http.get(urlBase+ "getAll");
	}
	
	loginService.register = function(user) {
		return $http.post(urlBase + "register", user)
	}
	
	return loginService;
	
}]);