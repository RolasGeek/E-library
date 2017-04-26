var app = angular.module("myApp");

app.factory('registerService',['$http', function ($http) {
	
	var registerService = {};
	var urlBase = 'rest/users/';
	
	registerService.register = function(user) {
		return $http.post(urlBase + "register", user)
	}
	
	
	return registerService;
	
}]);