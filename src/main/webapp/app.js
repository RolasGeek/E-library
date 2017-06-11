var app = angular.module("myApp", ['ui.router', 'ui.bootstrap', 'ngResource', 'ngCookies']);

app.factory('httpRequestInterceptor', function () {
    return {
        request: function (config) {

            config.headers['Authorization'] = 'Basic d2VudHdvcnRobWFuOkNoYW5nZV9tZQ==';

            return config;
        }
    };
});

app.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $httpProvider) {
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
        .state('register', {
            url: '/register',
            templateUrl: 'pages/register/register.html',
            controller: 'registerController',
            cache: false
        })
        .state('editProfile', {
            url: '/editProfile',
            templateUrl: 'pages/editProfile/editProfile.html',
            controller: 'editProfileController',
            cache: false
        })
        .state('insertbook', {
            url: '/insertbook',
            templateUrl: 'pages/insertBook/insertBook.html',
            controller: 'insertBookController',
            cache: false
        })
        .state('bookList', {
            url: '/list?searchQuery',
            params: {searchQuery: null},
            templateUrl: 'pages/booksList/booksList.html',
            controller: 'listController',
            cache: false
        })
        .state('openBook', {
            url: '/openBook/{bookId}',
            templateUrl: 'pages/openBook/openBook.html',
            controller: 'openBookController',
            cache: false
        })
        .state('editBook', {
            url: '/editBook/{bookId}',
            templateUrl: 'pages/editBook/editBook.html',
            controller: 'editBookController',
            cache: false
        })
        .state('rentBook',{
            url: '/rentBook/{bookId}',
            templateUrl: 'pages/rentBook/rentBook.html',
            controller: 'rentBookController',
            cache: false
        })
    ;


}]);

app.run(function ($rootScope, $cookieStore, $state, $location, $http) {
    if (!$cookieStore.get('userData')) {
        $location.path('/login');
    } else {
        $rootScope.profile = $cookieStore.get('userData');
        $http.defaults.headers.common.Authorization = $cookieStore.get('token');
        $rootScope.logedIn = true;
    }

    $rootScope.libraryName = "KTU biblioteka";
    $rootScope.libraryAddress = "K. Donelaičio g. 73, LT-44249 Kaunas";

    $rootScope.$on('$stateChangeStart', function (evt, toState, toParams, fromState, fromParams) {
        if ($cookieStore.get('userData')) {
            $rootScope.profile = $cookieStore.get('userData');
            $rootScope.logedIn = true;
        }

        if (toState.name === 'login') {
            if ($cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go(fromState);
            }
        } else if (toState.name === 'home') {
            if (!$cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go('login');
            }
        } else if (toState.name === 'profile') {
            if (!$cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go('login');
            }
        } else if (toState.name === 'register') {
            if ($cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go(fromState);
            }
        } else if (toState.name === 'editProfile') {
            if (!$cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go('login');
            }
        } else if (toState.name === 'insertbook') {
            if (!$cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go('login');
            }
        } else if (toState.name === 'openBook') {
            if (!$cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go('login');
            }
        } else if (toState.name === 'editBook') {
            if (!$cookieStore.get('userData')) {
                evt.preventDefault();
                $state.go('login');
            }
        } else if (toState.name === 'rentBook'){
            if (!$cookieStore.get('userData')){
                evt.preventDefault();
                $state.go('login');
            }
        }


    });
});






