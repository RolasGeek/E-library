var app = angular.module("myApp");

app.controller('usersController', ['$scope', '$state', '$rootScope', '$cookieStore', '$location', 'BookService', '$stateParams','LoginService', function ($scope, $state, $rootScope, $cookieStore, $location, BookService, $stateParams, LoginService) {
	getUsers();
	$scope.render = false;
	$scope.levels = [{level: 0, type: "User"},{level: 1, type : "Emploee"},{level: 2, type : "Manager"}]
	console.log($scope.levels);
	
	function getUsers() {
		LoginService.getAll().success(function(response){
			console.log(response);
			$scope.users = response;
		})
	};
	
	$scope.updateUser = function(user) {
		LoginService.updateUser(user).success(function(response) {
			$scope.render = true;
			$scope.alertclass="alert alert-success";
			$scope.alertmessage = "User changed";
			getUsers();
		});
	};
	

}]);