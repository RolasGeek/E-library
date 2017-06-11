var app = angular.module("myApp");

app.controller('profileController', ['$scope', '$rootScope', '$cookieStore', '$location', 'BookService', function($scope, $rootScope, $cookieStore, $location, BookService) {
	$scope.username = $rootScope.profile.username;
	$scope.email = $rootScope.profile.email;

	function getRents(){
		BookService.getRents($scope.username).success(function(data){
			$scope.rents = data;
        });
    }

    getRents();

	$scope.editProfile = function(){
		$location.path('/editProfile');
	}
}]);