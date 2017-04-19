

var app = angular.module("myApp", ['ui.router', 'ngResource', 'ngCookies']);

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    
	$urlRouterProvider.otherwise('/home');
    $stateProvider
    .state('login', {
            url: '/login',
            templateUrl: 'pages/login/login.html',
            controller: 'loginController'
        })
    .state('home', {
    	url: '/home',
    	templateUrl: 'pages/home/home.html',
    	controller: 'homeController',
    })
    .state('profile', {
    	url: '/home/profile',
    	templateUrl: 'pages/profile/profile.html',
    	controller: 'profileController'
    });
    
    
}]);

app.run(function($rootScope, $cookieStore, $state, $location) {
	if(!$cookieStore.get('userData')) {
		$location.path('/login');
	} else {
		$rootScope.profile = $cookieStore.get('userData');
  		$rootScope.logedIn = true;
	}
	
	  $rootScope.$on('$stateChangeStart', function(evt, toState, toParams, fromState, fromParams) {
		  	if($cookieStore.get('userData')){
		  		$rootScope.profile = $cookieStore.get('userData');
		  		$rootScope.logedIn = true;
		  		console.log($rootScope.profile);
		  	} 
		  	
		  	if(toState.name == 'login') {
		  		if($cookieStore.get('userData')) {
		  			evt.preventDefault();
		  			$state.go(fromState);
		  		}
		  	} else if (toState.name == 'home') {
		  		if(!$cookieStore.get('userData')) {
		  			evt.preventDefault();
		  			$state.go('login');
		  		}
		  	} else if(toState.name == 'profile') {
		  		if(!$cookieStore.get('userData')) {
		  			evt.preventDefault();
		  			$state.go('login');
		  		}
		  	}
		  	
		  

	  });
	});






