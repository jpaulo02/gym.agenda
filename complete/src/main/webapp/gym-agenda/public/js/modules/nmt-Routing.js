angular.module('nmtApp')
.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){
	//Redirect to homepage
	$urlRouterProvider.when('', 'login');
	$urlRouterProvider.when('/', 'login');

	$urlRouterProvider.otherwise("/404");

	$stateProvider
	.state('404', {
		url: '/404',
		templateUrl: '',
		data: {
			contentPages: 1,
		}
	})	
	.state('login', {
		url: '/login',
		templateUrl: 'views/login.html',
		data: {
			contentPages: 1,
		}
	})
	.state('home', {
		url: '/home',
		templateUrl: 'views/home.html',
		controller: 'MainController',
		data: {
			contentPages: 1,
		}
	})		
	.state('dashboard', {
		url: '/dashboard/:username',
		templateUrl: 'views/dashboard.html',
		controller: 'DashboardController',
		data: {
			contentPages: 1,
		}
	})	
	.state('arms', {
		url: '/arms',
		templateUrl: 'views/arms.html',
		controller: 'WorkoutsController',
		data: {
			contentPages: 1,
		}
	})	
	.state('welcome', {
		url: '/welcome/:username',
		templateUrl: 'views/welcome.html',
		controller: 'WelcomeController',
		data: {
			contentPages: 1,
		}
	});
}]);