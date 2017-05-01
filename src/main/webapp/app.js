

var app = angular.module("myApp", ['ui.router', 'ngResource', 'ngCookies']);

app.factory('httpRequestInterceptor', function () {
	  return {
	    request: function (config) {

	      config.headers['Authorization'] = 'Basic d2VudHdvcnRobWFuOkNoYW5nZV9tZQ==';

	      return config;
	    }
	  };
	});

app.config(['$stateProvider','$urlRouterProvider', '$httpProvider', function($stateProvider, $urlRouterProvider, $httpProvider) {
	$urlRouterProvider.otherwise('/home');
    $stateProvider
    .state('login', {
            url: '/login',
            templateUrl: 'pages/login/login.html',
            controller: 'loginController',
            cache: false
        })
    .state('home', {
    	url: '/home',
    	templateUrl: 'pages/home/home.html',
    	controller: 'homeController',
    	cache: false
    })
    .state('profile', {
    	url: '/home/profile',
    	templateUrl: 'pages/profile/profile.html',
    	controller: 'profileController',
    	cache: false
    })
    .state('register',{
    	url: '/register',
    	templateUrl: 'pages/register/register.html',
    	controller: 'registerController',
    	cache: false
    })
    .state('editProfile',{
    	url: '/editProfile',
    	templateUrl: 'pages/editProfile/editProfile.html',
    	controller: 'editProfileController',
    	cache: false
    });
    
    
}]);

app.run(function($rootScope, $cookieStore, $state, $location, $http) {
	$http.defaults.headers.common.Authorization = 'Basic YmVlcDpib29w';
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
		  	} else if (toState.name == 'register'){
		  		if($cookieStore.get('userData')){
		  			evt.preventDefault();
		  			$state.go(fromState);
		  		}
		  	} else if (toState.name == 'editProfile'){
		  		if(!$cookieStore.get('userData')){
		  			evt.preventDefault();
		  			$state.go('login');
		  		}
		  	}
		  	
		  

	  });
	});






